package com.ebay.risk.normalize.udf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.hadoop.io.Text;
import org.junit.Test;

public class RiskNormalizePhoneTest {

  RiskNormalizePhone riskNormalizePhone = new RiskNormalizePhone();

  @Test
  public void testNormalize() {
    // TODO(lehao): fix this bug.
    // assertCase("1", "[614]267-7581", "6142677581");
    // assertCase("1", "(987) 987 - 9898", "9879879898");
    assertCase(45L ,"|15900000000", true, "15900000000");
    assertCase(1L, "+000234324-32432111", true, "2343243243");
    assertCase(1L, "(+01)716-508-0538", true, "7165080538");
    assertCase(1L, "(+01)716-508-0538-0-1-4", true, "7165080538");
    assertCase(45L, "+86 18621910245", true, "18621910245");
    assertCase(45L, "000-186-219-10245", true, "18621910245");
    assertCase(1L, "[614]267-7581", true, "6142677581");
    assertCase(147L, "5999 515 - 6667", true, "59995156667");
  }

  @Test
  public void testIfAnyNull() {
    assertCase(45L ,null, true, null);
    assertCase(null ,"|15900000000", true, null);
  }

  @Test
  public void testSomeInvalidCase() {
    // For phone numbers which are too short, we regarded as invalid
    // when nullIfInvalid == true, return null
    assertCase(1L, "123", true, null);
    // when nullIfInvalid == false, return its original value
    assertCase(1L, "123", false, "123");
  }

  private void assertCase(Long countryId, String phoneNumber, boolean nullIfInvalid, String expected) {
    Text result = riskNormalizePhone.evaluate(countryId, phoneNumber == null ? null : new Text(phoneNumber), nullIfInvalid);
    if (expected == null) {
      assertNull(result);
    } else {
      assertEquals(new Text(expected), result);
    }
  }
}