package com.ebay.risk.normalize.hive.v4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.hadoop.io.Text;
import org.junit.Test;

public class RiskNormalizePhoneTest {

  RiskNormalizePhone riskNormalizePhone = new RiskNormalizePhone();

  @Test
  public void testNormalize() {
    //the area code and the next 3  numbers after the area code are same,
    assertCase(1L, "(987) 987 - 9898", true, -1, "9879879898");
    assertCase(1L, "310|310|3712", true, -1, "3103103712");
    //duplicate area code
    assertCase(1L, "(212)212 - 8623121", true, -1, "2128623121");
    //country id -999
    assertCase(-999L, "(256) 577 - 1132", true, -1, "2565771132");
    assertCase(-999L, "(817) 980 - 0683", true, -1, "8179800683");
    //phone length limitation  and invalid country id
    assertCase(-999L, "296", true, 8, null);
    assertCase(-999L, "(817) 980 - 0683", true, 8, "8179800683");
    //phone number start with country code
    assertCase(92L, "85248523", true, 8, "85248523");
    assertCase(145L, "(977)(01)438", true, 8, "97701438");
    //input phone number length is invalid
    assertCase(9L, "666", true, 8, null);
    assertCase(77L, "55544", true, 8, null);
    // phone number with special character
    assertCase(45L, "|15900000000", true, -1, "15900000000");
    assertCase(1L, "[614]267-7581", true, -1, "6142677581");
    //US phone number with multiple 0 or 1
    assertCase(1L, "+000234324-32432111", true, -1, "2343243243");
    assertCase(1L, "+1111234324-32432111", true, -1, "2343243243");
    assertCase(1L, "(+01)716-508-0538", true, -1, "7165080538");
    assertCase(1L, "(+01)716-508-0538-0-1-4", true, -1, "7165080538");
    // chinese phone test
    assertCase(45L, "+86 18621910245", true, -1, "18621910245");
    assertCase(45L, "000-186-219-10245", true, -1, "18621910245");
    assertCase(45L, "86233333385248523", true, -1, "2333333852");
    // google parse phone number error
    assertCase(147L, "5999 515 - 6667", true, -1, "59995156667");
    //add very long phone number
    String tempPhone = "5999515699";
    for (int i = 0; i < 300; i++) {
      tempPhone += "8";
      assertCase(1L, tempPhone, true, -1, "5999515699");
    }
    assertCase(104L, "81-0722", false, 5, "810722");
    //assertCase(104L, "81-0722", true, 5, "");
    assertCase(9L, "666", true, 4, null);
    assertCase(9L, "666", false, 4, "666");
    //US or CA too short phone, set the minimum length cut off is 10
    assertCase(1L, "705247509", true, 8, "705247509");
    //phone number start with country code and google can't parse it
    assertCase(77L, "0049-30-37401891", true, 8, "3037401891");
    assertCase(180L, "(65) 3858035", true, 8, "65385803");
    assertCase(95L, "91-22-6191785", true, 8, "9122619178");
    assertCase(95L, "9102126745", true, 8, "9102126745");
    assertCase(104L, "00-81-6723-0001", true, 8, "67230001");
    assertCase(95L, "9102126745", true, 8, "9102126745");

    assertCase(178L, "8033714665", true, 8, "8033714665");
  }

  @Test
  public void testIfAnyNull() {
    assertCase(45L, null, true, -1, null);
    assertCase(null, "|15900000000", true, -1, null);
  }

  @Test
  public void testSomeInvalidCase() {
    // For phone numbers which are too short, we regarded as invalid
    // when nullIfInvalid == true, return null
    assertCase(1L, "123", true, 8, null);
    // when nullIfInvalid == false, return its original value
    assertCase(1L, "123", false, -1, "123");
  }

  private void assertCase(Long countryId, String phoneNumber, boolean nullIfInvalid,
      int useStrictLengthLimit, String expected) {
    Text result = riskNormalizePhone
        .evaluate(countryId, phoneNumber == null ? null : new Text(phoneNumber), nullIfInvalid,
            useStrictLengthLimit);
    if (expected == null) {
      assertNull(result);
    } else {
      assertEquals(new Text(expected), result);
    }
  }
}