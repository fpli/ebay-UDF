package com.ebay.risk.normalize;


import static com.ebay.risk.normalize.N11nExceptionCodeEnum.EMAILVERSION;

import com.ebay.risk.exceptions.ClientException;
import com.ebay.risk.n11n.api.NormalizationLibrary;
import com.ebay.risk.n11n.api.entity.input.EmailInput;
import com.ebay.risk.n11n.api.entity.input.Input;
import com.ebay.risk.n11n.api.entity.result.EmailResult;
import com.ebay.risk.normalize.enums.NormalizeStrategyEnum;
import io.micrometer.core.annotation.Timed;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.EmailValidator;

public class EmailNormalizer implements NormalizationLibrary {

  private static final String Gmail = "gmail";
  private static final String Yahoo = "yahoo";
  private static final String GoogleEmail = "googlemail";
  private static final String Hotmail = "hotmail";
  private static final String Outlook = "outlook";
  private static final String YANDEX_SERVER = "yandex";
  private static final String LIVE_SERVER = "live";
  private static final String IG_DOMAIN = "ig.com.br";
  private static final String YA_DOMAIN = "ya.ru";
  private static final String NAROD_DOMAIN = "narod.ru";

  private static Set<String> domainSet = new HashSet<>();
  private EmailValidator validator = EmailValidator.getInstance();

  static {
    domainSet.add(Gmail);
    domainSet.add(Yahoo);
    domainSet.add(GoogleEmail);
    domainSet.add(Hotmail);
    domainSet.add(Outlook);
    domainSet.add(YANDEX_SERVER);
    domainSet.add(LIVE_SERVER);
    domainSet.add(IG_DOMAIN);
    domainSet.add(YA_DOMAIN);
    domainSet.add(NAROD_DOMAIN);
  }

  @Override
  @Timed(value = "n11n.lib.email.timer", histogram = true)
  public com.ebay.risk.n11n.api.entity.NormalizationResult normalize(Input input)
      throws ClientException {
    EmailInput emailInput = (EmailInput) input;
    NormalizationResult normalizedresult = normalize(NormalizeStrategyEnum.DEFAULT, 1,
        emailInput.getEmail());
    String[] strings = normalizedresult.getNormalizedValue().split("@");
    if (strings.length != 2) {
      throw new ClientException(400, "Email is invalid",
          normalizedresult.getNormalizedValue());
    }
    EmailResult emailResult = EmailResult.builder()
        .normalizedEmail(normalizedresult.normalizedValue)
        .localPart(strings[0])
        .domainPart(strings[1])
        //integrate xID apache validation
        .isValidEmail(validator.isValid(normalizedresult.normalizedValue))
        .build();
    return com.ebay.risk.n11n.api.entity.NormalizationResult.builder()
        .normalized(normalizedresult.isNormalized()).result(emailResult).build();
  }

  public NormalizationResult normalize(NormalizeStrategyEnum strategy,
      int version, String... keys) throws ClientException {
    String source = keys[0];
    if (version == 1 && strategy == NormalizeStrategyEnum.DEFAULT) {
      source = source.toLowerCase(Locale.ENGLISH).trim();
      String emailAddressSplit[] = source.split("@");
      if (emailAddressSplit.length == 0) {
        throw new NormalizationBadRequestException("email format is invalid", null);
      }
      String emailID = emailAddressSplit[0];
      if (StringUtils.isBlank(emailID)) {
        throw new NormalizationBadRequestException("email id is missing", null);
      }
      if (multiAt(source)) {
        throw new NormalizationBadRequestException("email format is invalid", null);
      }
      String server = extractEmailServer(source);
      String refinedEmailId = replaceTabAndSpace(replacePlus(replaceDot(emailID), server));
      if (refinedEmailId.length() == 0) {
        throw new NormalizationBadRequestException("email id is invalid", null);
      }
      String emailDomain = emailAddressSplit[1];
      if ((Gmail.equals(server) || Yahoo.equals(server))) {
        String result = refinedEmailId + "@" + emailDomain;
        return NormalizationResult.builder().normalized(true).normalizedValue(result).valid(true)
            .build();
      }
      if (GoogleEmail.equals(server)) {
        String result =
            refinedEmailId + "@" + "gmail" + emailDomain.substring(10);
        return NormalizationResult.builder().normalized(true).normalizedValue(result).valid(true)
            .build();
      }
      if (Hotmail.equals(server) || Outlook.equals(server)) {
        //these two server don't need to remove dot
        String result = replaceTabAndSpace(replacePlus(emailID, server)) + "@" + emailDomain;
        return NormalizationResult.builder().normalized(true).normalizedValue(result).valid(true)
            .build();
      }
      // QINLING-1473: To support xID email normalization strategy
      if (YANDEX_SERVER.equals(server) || LIVE_SERVER.equals(server)) {
        String result = replaceTabAndSpace(replacePlus(emailID, server)) + "@" + emailDomain;
        return NormalizationResult.builder().normalized(true).normalizedValue(result).valid(true)
            .build();
      }
      if (IG_DOMAIN.equals(emailDomain) || YA_DOMAIN.equals(emailDomain) || NAROD_DOMAIN
          .equals(emailDomain)) {
        String result = replaceTabAndSpace(replacePlus(emailID, emailDomain)) + "@" + emailDomain;
        return NormalizationResult.builder().normalized(true).normalizedValue(result).valid(true)
            .build();
      }
      return NormalizationResult.builder().normalized(true).normalizedValue(source).valid(true)
          .build();
    }
    throw new ClientException(EMAILVERSION.id(), EMAILVERSION.message(), source);
  }

  /**
   * This method takes email as argument and returns the server.
   */
  private String extractEmailServer(String emailAddress) throws NormalizationBadRequestException {
    if (StringUtils.isBlank(emailAddress)) {
      throw new NormalizationBadRequestException("email address is missing", null);
    }
    if (emailAddress.contains("@")) {
      String emailAddressSplit[] = emailAddress.split("@");
      if (emailAddressSplit.length > 1 && emailAddressSplit[1].contains(".")) {
        return emailAddressSplit[1].substring(0, emailAddressSplit[1].indexOf("."));
      }
      throw new NormalizationBadRequestException("email address should include domain after @",
          null);
    }
    throw new NormalizationBadRequestException("email address should include @", null);
  }

  private boolean multiAt(String email) {
    int count = 0;
    for (char ch : email.toCharArray()) {
      if (ch == '@') {
        count++;
      }
      if (count > 1) {
        return true;
      }
    }
    return false;
  }

  public static String replacePlus(String str, String sever) {
    if (domainSet.contains(sever)) {
      /*
        To fix a strange exception on hadoop cluster.
        Caused by: java.lang.ArrayIndexOutOfBoundsException: 0
          at com.ebay.risk.normalize.EmailNormalizer.replacePlus(EmailNormalizer.java:158)
          at com.ebay.risk.normalize.EmailNormalizer.normalize(EmailNormalizer.java:85)
          at com.ebay.risk.normalize.EmailNormalizer.normalize(EmailNormalizer.java:51)
       */
      String[] strings = str.split("\\+");
      if (strings.length == 0) {
        return "";
      } else {
        return strings[0];
      }
    }
    return str;
  }

  public static String replaceTabAndSpace(String str) {
    return str.replaceAll("\\s+", "");
  }

  public static String replaceDot(String str) {
    return str.replaceAll("\\.", "");
  }

}
