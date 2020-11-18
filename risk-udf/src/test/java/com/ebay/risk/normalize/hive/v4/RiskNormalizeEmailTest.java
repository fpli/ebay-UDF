package com.ebay.risk.normalize.hive.v4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.hadoop.io.Text;
import org.junit.Test;

public class RiskNormalizeEmailTest {

  RiskNormalizeEmail riskNormalizeEmail = new RiskNormalizeEmail();

  @Test
  public void testNormalize() {
    //return raw email when nullifInvalid false apache validation return true
    assertCase("@hotmail.com", true, true, null);
    assertCase("www.laurynrush@hotmail", true, true, null);
    assertCase("80383@deleted", true, true, null);
    //remove dot
    assertCase("www.igor76@yahoo.com", true, true, "wwwigor76@yahoo.com");
    assertCase("mixer1.geo@yahoo.com", true, true, "mixer1geo@yahoo.com");
    //replace googlemail with gmail
    assertCase("ebuhl61@googlemail.com", true, true, "ebuhl61@gmail.com");
    assertCase("gartzpeter@googlemail.com", true, true, "gartzpeter@gmail.com");
    //remove chars after +
    assertCase("mp.q7+3a9@yandex.com.tr", true, true, "mp.q7@yandex.com.tr");
    assertCase("fohok.golo+huz11@yandex.com.tr", true, true, "fohok.golo@yandex.com.tr");
    //return null when nullIfInvalid true and  and use apache lib is true
    assertCase("w.d.@lycosmail.com", true, true, null);
  }

  @Test
  public void testWithParams() {
    //nullIfInvalid=true useApache=true
    assertCase("millof5@.msn.com", true, true, null);
    assertCase("lehzhu@ebay.com", true, true, "lehzhu@ebay.com");
    assertCase("lehzhu@", true, true, null);
    //nullIfInvalid=false useApache=false
    assertCase("millof5@.msn.com", true, false, "millof5@.msn.com");
    assertCase("lehzhu@ebay.com", false, false, "lehzhu@ebay.com");
    assertCase("millof5@", false, false, "millof5@");
    //nullIfInvalid=true useApache=false
    assertCase("millof5@.msn.com", true, false, "millof5@.msn.com");
    assertCase("lehzhu@ebay.com", true, false, "lehzhu@ebay.com");
    assertCase("lehzhu@", true, false, null);
    //nullIfInvalid=false useApache=true
    assertCase("millof5@.msn.com", false, true, "millof5@.msn.com");
    assertCase("lehzhu+test@gmail.com", false, true, "lehzhu@gmail.com");
    assertCase("lehzhu@", false, true, "lehzhu@");
  }

  @Test
  public void testSpecialChars() {
    // Reported from XID, using true, true
    assertCase("\u0014wostepr1@aol.com", true, true, "wostepr1@aol.com");
    assertCase("\u0004theo23@hotmail.com", true, true, "theo23@hotmail.com");
    assertCase("hakiba311@yahoo.com\u0018", true, true, "hakiba311@yahoo.com");
  }

  private void assertCase(String rawEmail, boolean nullIfInvalid, boolean useApacheLib,
      String expected) {
    Text result = riskNormalizeEmail
        .evaluate(rawEmail == null ? null : new Text(rawEmail), nullIfInvalid, useApacheLib);
    if (expected == null) {
      assertNull(result);
    } else {
      assertEquals(new Text(expected), result);
    }
  }

  @Test
  public void testValidatorVersion() {
    // When commons-validator == 1.4.1, will return tapia5123@.com
    // When commons-validator == 1.2, will return null
    assertCase("tapia5123@.com", true, true, null);
  }
}
