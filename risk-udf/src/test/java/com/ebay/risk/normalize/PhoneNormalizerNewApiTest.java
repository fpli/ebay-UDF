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
    assertCase(45, "+86008840624+47097", "00884062447097", "");
    assertCase(1, ")(-00 )-||19881168693", "9881168693", "+19881168693");
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


