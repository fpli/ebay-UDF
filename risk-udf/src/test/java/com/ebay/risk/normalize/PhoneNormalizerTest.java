package com.ebay.risk.normalize;

import static org.junit.Assert.assertEquals;

import com.ebay.risk.exceptions.ClientException;
import com.ebay.risk.normalize.enums.NormalizeStrategyEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class PhoneNormalizerTest {


  @InjectMocks
  private PhoneNormalizer phoneNormalizer;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void normalizeTest() throws ClientException {
    // TODO(lehao): fix this bug.
//    assertCase("1", "[614]267-7581", "6142677581");
//    assertCase("1", "(987) 987 - 9898", "9879879898");
    assertCase("1", "+000234324-32432111", "2343243243");
    assertCase("1", "(+01)716-508-0538", "7165080538");
    assertCase("1", "(+01)716-508-0538-0-1-4", "7165080538");
    assertCase("45", "+86 18621910245", "18621910245");
    assertCase("45", "000-186-219-10245", "18621910245");
    assertCase("1", "[614]267-7581", "6142677581");
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


  @Test(expected = ClientException.class)
  public void normalizeTestInvalidUSPhoneNumber() throws ClientException {
    phoneNormalizer.normalize(NormalizeStrategyEnum.DEFAULT, 1, "1", "23");
  }

  @Test(expected = ClientException.class)
  public void normalizeTestInvalidUSPhoneNumber2() throws ClientException {
    phoneNormalizer.normalize(NormalizeStrategyEnum.DEFAULT, 1, "1", "00|| ( +-01353849932");
  }

  @Test
  public void normalizeTestDuplicateAreaCode() throws ClientException {
    PhoneNormalizationResult phoneNormalizationResult = phoneNormalizer
        .normalize(NormalizeStrategyEnum.DEFAULT, 1, "1", "(212 ) 212 -  862 3121");
    Assert.assertEquals("2128623121", phoneNormalizationResult.getNormalizedValue());
  }
}


