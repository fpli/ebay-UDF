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
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import io.micrometer.core.annotation.Timed;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class PhoneNormalizer implements NormalizationLibrary {

  private final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
  private static Method phoneUtilPhaseMethodOld;
  private static Method phoneUtilPhaseMethodNew;
  private static Method phoneUtilPhaseWithRawMethodOld;
  private static Method phoneUtilPhaseWithRawMethodNew;

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
    try {
      phoneUtilPhaseWithRawMethodOld = PhoneNumberUtil.class
          .getMethod("parseAndKeepRawInput", CharSequence.class, String.class);
    } catch (NoSuchMethodException ignore) {
    }
    try {
      phoneUtilPhaseWithRawMethodNew = PhoneNumberUtil.class
          .getMethod("parseAndKeepRawInput", String.class, String.class);
    } catch (NoSuchMethodException ignore) {
    }
    if (phoneUtilPhaseWithRawMethodOld == null && phoneUtilPhaseWithRawMethodNew == null) {
      throw new RuntimeException("Cannot get method of PhoneNumberUtil.parseAndKeepRawInput()");
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
      boolean isUSorCA = countryEnum.equals(CountryCodeEnum.US) || countryEnum.equals(CountryCodeEnum.CA);

      String phoneNumber = removeNodigitChars(phone);
      HashSet<Character> leadingChartsToRemove = new HashSet<>();
      if (isUSorCA) {
        // Remove leading 0,1 until 10 digits
        // To keep US/CA phone number in a valid form (seems like)
        leadingChartsToRemove.add('0');
        leadingChartsToRemove.add('1');
        phoneNumber = removeLeadingChars(phoneNumber, leadingChartsToRemove, 10);
        // edge case normalize
        phoneNumber = removeDuplicateAreaCode(phoneNumber);
      } else {
        // Remove all leading zeros
        leadingChartsToRemove.add('0');
        phoneNumber = removeLeadingChars(phoneNumber, leadingChartsToRemove, 0);
      }
      if (phoneNumber.length() > 17) {
        phoneNumber = phoneNumber.substring(0, 17);
      }
      if (StringUtils.isEmpty(phoneNumber)) {
        throw new NormalizationBadRequestException("phone number is invalid: " + phoneNumber);
      }

      Optional<GoogleParseResult> result = tryRemoveTailDigitsAccordingToLibPhoneNumber(
          phoneNumber, countryEnum);

      String retPhoneNumber = result.isPresent() ? result.get().getNationalNumber() : phoneNumber;
      int retCountryCodeE164 = result.isPresent() ? result.get().getCountryCode() : 0;
      String retPhoneNumberE164 = getE164Format(retPhoneNumber, countryEnum);

      return PhoneNormalizationResult.phoneNRBuilder()
          .normalized(result.isPresent())
          .normalizedValue(retPhoneNumber)
          .valid(true)
          .countryCodeE164(retCountryCodeE164)
          .phoneNumberE164Format(retPhoneNumberE164)
          .build();
    }
    throw new ClientException(PHONEVERSION.id(), PHONEVERSION.message(), phone);
  }

  private String getE164Format(String phoneNumber, CountryCodeEnum countryEnum) {
    Phonenumber.PhoneNumber phoneNumberLib = null;
    String phoneNumberE164 = "";
    Optional<PhoneNumber> pareRet = parsePhone(phoneNumber, countryEnum);
    if (pareRet.isPresent()) {
      phoneNumberE164 = phoneUtil.format(pareRet.get(), PhoneNumberUtil.PhoneNumberFormat.E164);
    }
    return phoneNumberE164;
  }

  private String removeNodigitChars(String str) {
    return str.replaceAll("[^0-9]", "");
  }

  /**
   * Remove a string's leading chars in the charSet, until {@code length <= minReturnLength}
   */
  public static String removeLeadingChars(String str, Set<Character> charSet, int minReturnLength) {
    int subStrStart = 0;
    int totalLen = str.length();
    while (totalLen - subStrStart > minReturnLength) {
      if (charSet.contains(str.charAt(subStrStart))) {
        subStrStart++;
      } else {
        break;
      }
    }
    return str.substring(subStrStart);
  }

  public Optional<GoogleParseResult> tryRemoveTailDigitsAccordingToLibPhoneNumber(
      String phoneNumber, CountryCodeEnum countryEnum) {
    boolean isUSorCA = countryEnum.equals(CountryCodeEnum.US) || countryEnum.equals(CountryCodeEnum.CA);
    // Because we have to tolerant US numbers like 000 111 1234, so if there are 10 digits, we will
    // keep the phone number don't remove digits.
    if (isUSorCA && phoneNumber.length() == 10) {
      return Optional.of(GoogleParseResult.builder()
          .countryCode(1)
          .nationalNumber(phoneNumber)
          .build());
    }
    // Now start removing tail digits
    Optional<PhoneNumber> parsedNumber = parsePhoneWithRawString(phoneNumber, countryEnum.name());
    Optional<GoogleParseResult> result = Optional.empty();
    if (parsedNumber.isPresent()) {
      String countryCode = String.valueOf(parsedNumber.get().getCountryCode());
      String nationalNumber = String.valueOf(parsedNumber.get().getNationalNumber());
      //national number start with country code
      if (nationalNumber.startsWith(countryCode)) {
        String nationalNumSub = nationalNumber.substring(countryCode.length());
        result = tryNormalizeWithGoogleLib(nationalNumSub,
            countryEnum.name(), phoneUtil::isValidNumber);
        if (!result.isPresent()) {
          result = tryNormalizeWithGoogleLib(phoneNumber,
              countryEnum.name(), phoneUtil::isValidNumber);
          if (!result.isPresent()) {
            result = tryNormalizeWithGoogleLib(nationalNumSub,
                countryEnum.name(), phoneUtil::isPossibleNumber);
            if (!result.isPresent()) {
              result = tryNormalizeWithGoogleLib(phoneNumber,
                  countryEnum.name(), phoneUtil::isPossibleNumber);
            }
          }
        }
      } else {
        result = tryNormalizeWithGoogleLib(phoneNumber,
            countryEnum.name(), phoneUtil::isValidNumber);
        if (!result.isPresent()) {
          // isValidNumber strategy cannot find the result, fallback to isPossibleNumber
          result = tryNormalizeWithGoogleLib(phoneNumber,
              countryEnum.name(), phoneUtil::isPossibleNumber);
        }
      }
    }
    return result;
  }

  /**
   * Get CountryEnum by ebay's country id. -999 is US id request by xID
   */
  private static CountryCodeEnum ebayLegacyCountryIdToEnum(int ebayLegacyCountryId)
      throws NormalizationBadRequestException {
    CountryCodeEnum countryEnum;
    if (ebayLegacyCountryId == -999) {
      return CountryCodeEnum.US;
    }
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
    if (phoneNumber.length() >= 13) {
      try {
        int areaCode1 = Integer.parseInt(phoneNumber.substring(0, 3));
        int areaCode2 = Integer.parseInt(phoneNumber.substring(3, 6));
        if (areaCode2 >= 200 && areaCode1 == areaCode2) {
          return phoneNumber.substring(3);
        }
      } catch (NumberFormatException ex) {
        throw new NormalizationBadRequestException("phone number contains unexpected symbols ",
            phoneNumber);
      }
    }
    return phoneNumber;
  }

  /**
   * Remove leading zeros and ones until it <= 10 digits
   * Only works for US.
   */
  private String removeUSLeadingZeroAndOne(String phoneNumber) {
    int subStrStart = 0;
    int totalLen = phoneNumber.length();
    while (totalLen - subStrStart > 10) {
      if (phoneNumber.charAt(subStrStart) == '0' || phoneNumber.charAt(subStrStart) == '1') {
        subStrStart++;
      } else {
        break;
      }
    }
    return phoneNumber.substring(subStrStart);
  }


  /**
   * Keep removing last digits until Google isPossiblePhone returns true.
   *
   * @param countryName e.g. CN
   */
  private Optional<GoogleParseResult> tryNormalizeWithGoogleLib(String phoneNumber,
      String countryName, Function<PhoneNumber, Boolean> checker) {
    int shortestLen = 8;
    //US or CA phone, set the minimum cut off length  10
    if (countryName.equalsIgnoreCase("US") || countryName.equalsIgnoreCase("CA")) {
      shortestLen = 10;
    }
    String phoneTmp = phoneNumber;
    while (phoneTmp.length() >= shortestLen) {
      Optional<GoogleParseResult> parseResult = parseAndCheckByLibPhoneNumber(phoneTmp,
          countryName, checker);
      if (parseResult.isPresent()) {
        return parseResult;
      }
      // Remove last char and retry
      phoneTmp = phoneNumber.substring(0, phoneTmp.length() - 1);
    }
    return Optional.empty();
  }

  private Optional<PhoneNumber> parsePhone(String phoneNumber, CountryCodeEnum countryEnum) {
    Phonenumber.PhoneNumber phoneNumberLib = null;
    try {
      if (phoneUtilPhaseMethodNew != null) {
        phoneNumberLib = (PhoneNumber) phoneUtilPhaseMethodNew
            .invoke(phoneUtil, phoneNumber, countryEnum.name());
      } else {
        phoneNumberLib = (PhoneNumber) phoneUtilPhaseMethodOld
            .invoke(phoneUtil, phoneNumber, countryEnum.name());
      }
    } catch (IllegalAccessException | InvocationTargetException e) {
      log.warn("Cannot call libphonenumber:", e);
    }
    return Optional.ofNullable(phoneNumberLib);
  }

  private Optional<GoogleParseResult> parseAndCheckByLibPhoneNumber(String phoneNumber,
      String countryName, Function<PhoneNumber, Boolean> checker) {
    Optional<PhoneNumber> number = parsePhoneWithRawString(phoneNumber, countryName);
    if (!number.isPresent() || !checker.apply(number.get())) {
      return Optional.empty();
    }

    return Optional.of(GoogleParseResult.builder()
        .countryCode(number.get().getCountryCode())
        .nationalNumber(String.valueOf(number.get().getNationalNumber()))
        .build());
  }

  private Optional<PhoneNumber> parsePhoneWithRawString(String phoneNumber, String countryName) {
    Phonenumber.PhoneNumber phoneNumberLib = null;
    try {
      if (phoneUtilPhaseWithRawMethodNew != null) {
        phoneNumberLib = (PhoneNumber) phoneUtilPhaseWithRawMethodNew
            .invoke(phoneUtil, phoneNumber, countryName);
      } else {
        phoneNumberLib = (PhoneNumber) phoneUtilPhaseWithRawMethodOld
            .invoke(phoneUtil, phoneNumber, countryName);
      }
    } catch (IllegalAccessException | InvocationTargetException e) {
      log.warn("Cannot call libphonenumber:", e);
    }
    return Optional.ofNullable(phoneNumberLib);
  }


}
