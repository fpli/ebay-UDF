package com.ebay.risk.normalize;


import static com.ebay.risk.normalize.N11nExceptionCodeEnum.PHONEVERSION;

import com.ebay.cos.type.v3.base.CountryCodeEnum;
import com.ebay.risk.exceptions.ClientException;
import com.ebay.risk.n11n.api.NormalizationLibrary;
import com.ebay.risk.n11n.api.entity.input.Input;
import com.ebay.risk.n11n.api.entity.input.PhoneInput;
import com.ebay.risk.n11n.api.entity.result.PhoneResult;
import com.ebay.risk.normalize.enums.NormalizeStrategyEnum;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import io.micrometer.core.annotation.Timed;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class PhoneNormalizer implements NormalizationLibrary {

  private static Method phoneUtilPhaseMethodOld;
  private static Method phoneUtilPhaseMethodNew;

  static {
    // Why use reflection to call PhoneNumberUtil.parse?
    // Our light library has two uses cases:
    // 1. Normalization Service, which will depend on libphonenumber with POM specified version (8.10.x)
    // 2. Exported as a Hive UDF. On ebay's hadoop cluster, there are libphonenumber jar already
    //    loaded, whose version will be 3.x ~ 6.x
    // For the two versions of libphonenumber, a method's signature has changed:
    // old: PhoneNumberUtil.parse(String, String)
    // new: PhoneNumberUtil.parse(CharSequence, String)
    // We want to make our method call compatible with both new and old version of PhoneNumberUtil
    // So I try to make a reflection check which method exists, then call the method later.
    // This is not a POM issue, instead, is JVM class loading problem for environment we cannot
    // control.
    try {
      phoneUtilPhaseMethodNew = PhoneNumberUtil.class
          .getMethod("parse", CharSequence.class, String.class);
    } catch (NoSuchMethodException ignore) {
    }
    try {
      phoneUtilPhaseMethodOld = PhoneNumberUtil.class
          .getMethod("parse", String.class, String.class);
    } catch (NoSuchMethodException ignore) {
    }
    if (phoneUtilPhaseMethodOld == null && phoneUtilPhaseMethodNew == null) {
      throw new RuntimeException("Cannot get method of PhoneNumberUtil.parse()");
    }
  }

  @Override
  @Timed(value = "n11n.lib.phone.timer", histogram = true)
  public com.ebay.risk.n11n.api.entity.NormalizationResult normalize(Input input)
      throws ClientException {
    PhoneInput phoneInput = (PhoneInput) input;
    CountryCodeEnum countryCodeEnum = ebayLegacyCountryIdToEnum(phoneInput.getCountryId());
    PhoneNormalizationResult normalizedresult = normalize(NormalizeStrategyEnum.DEFAULT, 1,
        Integer.toString(countryCodeEnum.getLegacyCountryId()), phoneInput.getPhoneNumber());

    PhoneResult phoneResult = PhoneResult.builder()
        .countryId(countryCodeEnum.getLegacyCountryId())
        .nationalNumber(normalizedresult.normalizedValue)
        .countryCode(normalizedresult.getCountryCodeE164())
        .phoneNumberE164Format(normalizedresult.getPhoneNumberE164Format())
        .build();

    return com.ebay.risk.n11n.api.entity.NormalizationResult.builder()
        .normalized(normalizedresult.isNormalized())
        .result(phoneResult)
        .build();
  }

  /**
   * Normalize phone numbers.
   *
   * @param keys 0: countryId; 1: phone number (could starts with +xx, 00xx)
   */
  public PhoneNormalizationResult normalize(NormalizeStrategyEnum strategy, int version,
      String... keys) throws ClientException {
    String ebayCountryId = keys[0];
    String phone = keys[1];
    if (version == 1 && strategy == NormalizeStrategyEnum.DEFAULT) {
      CountryCodeEnum countryEnum = ebayLegacyCountryIdToEnum(ebayCountryId);
      char[] phoneChars = phone.toCharArray();
      String phoneNumber = removeNodigitChars(phoneChars);
      phoneNumber = removeZeroFromPrefix(phoneNumber);
      phoneNumber = phoneNumber.trim();
      if (StringUtils.isEmpty(phoneNumber)) {
        throw new NormalizationBadRequestException("phone number is invalid: " + phoneNumber);
      }
      PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
      PhoneNumber examplePhoneNumber = phoneUtil
          .getExampleNumberForType(countryEnum.name(), PhoneNumberType.FIXED_LINE_OR_MOBILE);
      String countryCodeStr = "";
      int countryCodePrefix = 0;
      if (examplePhoneNumber != null) {
        countryCodePrefix = examplePhoneNumber.getCountryCode();
        countryCodeStr = String.valueOf(countryCodePrefix);
      }
      if (phoneNumber.startsWith(countryCodeStr) && !countryEnum.equals(CountryCodeEnum.US)
          && !countryEnum.equals(CountryCodeEnum.CA)) {
        phoneNumber = phoneNumber.substring(countryCodeStr.length());
      }
      if (countryEnum.equals(CountryCodeEnum.US) || countryEnum.equals(CountryCodeEnum.CA)) {
        phoneNumber = phoneNumber.replaceAll("^[01]*", "");
        phoneNumber = removeDuplicateAreaCode(phoneNumber);
        if (phoneNumber.length() >= 10) {
          phoneNumber = phoneNumber.substring(0, 10);
        } else {
          throw new NormalizationBadRequestException(
              "this is not a valid CA or US phone number " + phoneNumber);
        }
      }
      if (StringUtils.isEmpty(phoneNumber)) {
        throw new NormalizationBadRequestException(
            "Phone number is normalized to empty, so it is invalid: " + phoneNumber);
      }
      if (phoneNumber.length() > 250) {
        phoneNumber = phoneNumber.substring(0, 250);
      }

      String phoneNumberE164 = getE164Format(phoneNumber, countryEnum);
      return PhoneNormalizationResult.phoneNRBuilder()
          .normalized(true)
          .normalizedValue(phoneNumber)
          .valid(true)
          .countryCodeE164(countryCodePrefix)
          .phoneNumberE164Format(phoneNumberE164)
          .build();
    }
    throw new ClientException(PHONEVERSION.id(), PHONEVERSION.message(), phone);
  }

  private String getE164Format(String phoneNumber, CountryCodeEnum countryEnum) {
    PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
    Phonenumber.PhoneNumber phoneNumberLib = null;
    String phoneNumberE164 = "";

    try {
      if (phoneUtilPhaseMethodNew != null) {
        phoneNumberLib = (PhoneNumber) phoneUtilPhaseMethodNew
            .invoke(phoneUtil, phoneNumber, countryEnum.name());
      } else {
        phoneNumberLib = (PhoneNumber) phoneUtilPhaseMethodOld
            .invoke(phoneUtil, phoneNumber, countryEnum.name());
      }
      phoneNumberE164 = phoneUtil.format(phoneNumberLib, PhoneNumberUtil.PhoneNumberFormat.E164);
      return phoneNumberE164;
    } catch (IllegalAccessException | InvocationTargetException e) {
      log.warn("Cannot call libphonenumber:", e);
    }
    return "";
  }

  private String removeNodigitChars(char[] phoneChars) {
    StringBuilder stringBuilder = new StringBuilder();
    for (char phoneChar : phoneChars) {
      if (Character.isDigit(phoneChar)) {
        stringBuilder.append(phoneChar);
      }
    }
    return stringBuilder.toString();
  }

  private String removeZeroFromPrefix(String phoneNumber) {
    char[] phoneChars = phoneNumber.toCharArray();
    boolean isLeadingZero = true;
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < phoneChars.length; i++) {
      if (phoneChars[i] != '0' || (!isLeadingZero && phoneChars[i] == '0')) {
        stringBuilder.append(phoneChars[i]);
        isLeadingZero = false;
      }
    }
    return stringBuilder.toString();
  }


  private static CountryCodeEnum ebayLegacyCountryIdToEnum(int ebayLegacyCountryId)
      throws NormalizationBadRequestException {
    CountryCodeEnum countryEnum;
    try {
      countryEnum = CountryCodeEnum.findByLegacyCountryId(ebayLegacyCountryId);
    } catch (NumberFormatException e) {
      throw new NormalizationBadRequestException("invalid country id");
    }
    if (countryEnum == null) {
      throw new NormalizationBadRequestException("invalid country id");
    }
    return countryEnum;
  }

  private static CountryCodeEnum ebayLegacyCountryIdToEnum(String ebayLegacyCountryId)
      throws NormalizationBadRequestException {
    if (StringUtils.isEmpty(ebayLegacyCountryId)) {
      throw new NormalizationBadRequestException("country id is missing");
    }
    return ebayLegacyCountryIdToEnum(Integer.parseInt(ebayLegacyCountryId));
  }

  private String removeDuplicateAreaCode(String phoneNumber)
      throws NormalizationBadRequestException {
    if (phoneNumber.length() >= 6) {
      try {
        int areaCode1 = Integer.parseInt(phoneNumber.substring(0, 3));
        int areaCode2 = Integer.parseInt(phoneNumber.substring(3, 6));
        if (areaCode1 >= 200 && areaCode2 >= 200 && areaCode1 == areaCode2) {
          return phoneNumber.substring(3);
        }
      } catch (NumberFormatException ex) {
        throw new NormalizationBadRequestException("phone number contains unexpected symbols ",
            phoneNumber);
      }
    }
    return phoneNumber;
  }

}
