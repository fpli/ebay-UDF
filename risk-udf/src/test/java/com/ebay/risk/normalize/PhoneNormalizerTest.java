package com.ebay.risk.normalize;

import static org.junit.Assert.assertEquals;

import com.ebay.cos.type.v3.base.CountryCodeEnum;
import com.ebay.risk.exceptions.ClientException;
import com.ebay.risk.normalize.enums.NormalizeStrategyEnum;
import com.google.i18n.phonenumbers.CountryCodeHelper;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.junit.Assert;
import org.junit.Test;

public class PhoneNormalizerTest {

  private PhoneNormalizer phoneNormalizer = new PhoneNormalizer();

  @Test
  public void normalizeTest() throws ClientException {
    assertCase("1", "+000234324-32432111", "2343243243");
    assertCase("1", "(+01)716-508-0538", "7165080538");
    assertCase("1", "(+01)716-508-0538-0-1-4", "7165080538");
    assertCase("45", "+86 18621910245", "18621910245");
    assertCase("45", "000-186-219-10245", "18621910245");
    assertCase("1", "[614]267-7581", "6142677581");
    assertCase("1", "(987) 987 - 9898", "9879879898");
    assertCase("-999", "(318) 925 - 9979", "3189259979");
    assertCase("145", "(977)(01)438", "97701438");
    assertCase("92", "85248523", "85248523");
    assertCase("1", "561.750.4226", "5617504226");
    assertCase("1", "+000234324-32432111", "2343243243");
    //QE P1 case https://wiki.vip.corp.ebay.com/pages/editpage.action?pageId=801927358
    assertCase("1", "(987)-987-9898", "9879879898");
    assertCase("1", "212-212-8623121", "2128623121");
    assertCase("-999", "(987)-987-9898", "9879879898");

    assertCase("1", "614\u0001 267\u0014-我7581", "6142677581");

  }

  @Test
  public void normalizeSomeFailedCase() throws ClientException {
    assertCase("147", "5999 515 - 6667", "59995156667");
  }

  private void assertCase(String countryId, String input, String expectedOutput)
      throws ClientException {
    NormalizationResult result = phoneNormalizer
        .normalize(NormalizeStrategyEnum.DEFAULT, 1, countryId, input);
    Assert.assertNotNull(result);
    assertEquals(expectedOutput, result.getNormalizedValue());
  }

  @Test(expected = ClientException.class)
  public void normalizeTestCountryCodeNull() throws ClientException {
    phoneNormalizer.normalize(NormalizeStrategyEnum.DEFAULT, 1, "", "23432432432111");
  }

  @Test(expected = ClientException.class)
  public void normalizeTestCountryCodeEnum() throws ClientException {
    phoneNormalizer.normalize(NormalizeStrategyEnum.DEFAULT, 1, "-1", "00023+432432432111");
  }


  @Test
  public void normalizeTestInvalidUSPhoneNumber() throws ClientException {
    phoneNormalizer.normalize(NormalizeStrategyEnum.DEFAULT, 1, "1", "23");
  }

  @Test
  public void normalizeTestInvalidUSPhoneNumber2() throws ClientException {
    phoneNormalizer.normalize(NormalizeStrategyEnum.DEFAULT, 1, "1", "00|| ( +-01353849932");
  }

  @Test
  public void normalizeTestDuplicateAreaCode() throws ClientException {
    PhoneNormalizationResult phoneNormalizationResult = phoneNormalizer
        .normalize(NormalizeStrategyEnum.DEFAULT, 1, "1", "(212 ) 212 -  862 3121");
    Assert.assertEquals("2128623121", phoneNormalizationResult.getNormalizedValue());
  }

  @Test
  public void findAllMissingCountryCode() {
    Map<String, Integer> googleRegionToCountryCode = new TreeMap<>();
    for (Entry<Integer, List<String>> entry : CountryCodeHelper.getCountryCodeToRegionCodeMap()
        .entrySet()) {
      for (String region : entry.getValue()) {
        googleRegionToCountryCode.put(region, entry.getKey());
      }
    }

    System.out.println("Only in eBay CountryCodeEnum:");
    for (CountryCodeEnum codeEnum : CountryCodeEnum.values()) {
      if (!googleRegionToCountryCode.containsKey(codeEnum.name())) {
        System.out.println(String
            .format("    %s %d %s", codeEnum.name(), codeEnum.getLegacyCountryId(),
                codeEnum.getName()));
      }
    }

    System.out.println("\n========================\n");

    System.out.println("Only in google libphonenumber:");
    for (Entry<String, Integer> entry : googleRegionToCountryCode.entrySet()) {
      try {
        CountryCodeEnum codeEnum = CountryCodeEnum.valueOf(entry.getKey());
      } catch (IllegalArgumentException e) {
        System.out.println(String.format("    %s %d", entry.getKey(), entry.getValue()));
      }
    }
  }

}


