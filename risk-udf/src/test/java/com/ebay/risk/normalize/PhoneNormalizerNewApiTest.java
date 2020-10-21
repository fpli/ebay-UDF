package com.ebay.risk.normalize;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.ebay.risk.exceptions.ClientException;
import com.ebay.risk.n11n.api.entity.NormalizationResult;
import com.ebay.risk.n11n.api.entity.input.PhoneInput;
import com.ebay.risk.n11n.api.entity.result.PhoneResult;
import org.junit.Assert;
import org.junit.Test;

public class PhoneNormalizerNewApiTest {

  private PhoneNormalizer phoneNormalizer = new PhoneNormalizer();

  @Test
  public void normalizeTest() throws ClientException {
    assertCase(1, "+000234324-32432111", "2343243243", "+12343243243");
    assertCase(1, "(+01)716-508-0538", "7165080538", "+17165080538");
    assertCase(1, "(+01)716-508-0538-0-1-4", "7165080538", "+17165080538");
    assertCase(45, "+86 18621910245", "18621910245", "+8618621910245");
    assertCase(45, "000-186-219-10245", "18621910245", "+8618621910245");
    assertCase(45, "+86008840624+47097", "884062447097", "+86884062447097");
    assertCase(1, ")(-00 )-||19881168693", "9881168693", "+19881168693");
  }

  @Test
  public void testWithTenDigitsUS() throws ClientException {
    // Here are some US numbers with 10 digits, but with leading 1/0
    // We need to keep them 10 digits, according to discussion with XID

    assertCase(1, "132 321 2144", "1323212144", "+11323212144");
    assertCase(1, "000 111 2322", "0001112322", "+10001112322");
    assertCase(1, "123 123 1234", "1231231234", "+11231231234");
    assertCase(1, "030 252 4020", "0302524020", "+10302524020");
    assertCase(1, "113 168 8499", "1131688499", "+11131688499");
    // Remove to 10 digits
    assertCase(1, "010101 113 168 8499", "1131688499", "+11131688499");
    assertCase(1, "3 168 8499", "31688499", "+131688499");
  }

  private void assertCase(int countryId, String input, String expectedNationalNumber,
      String expectedE164Format) throws ClientException {
    NormalizationResult result = phoneNormalizer
        .normalize(PhoneInput.builder().countryId(countryId).phoneNumber(input).build());
    Assert.assertNotNull(result);
    assertTrue(result.getResult() instanceof PhoneResult);
    PhoneResult phoneResult = (PhoneResult) result.getResult();
    assertEquals(expectedNationalNumber, phoneResult.getNationalNumber());
    assertEquals(expectedE164Format, phoneResult.getPhoneNumberE164Format());
  }
}


