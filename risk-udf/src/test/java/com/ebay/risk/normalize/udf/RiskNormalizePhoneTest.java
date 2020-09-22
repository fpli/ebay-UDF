package com.ebay.risk.normalize.udf;

import static org.junit.Assert.assertEquals;

import org.apache.hadoop.io.Text;
import org.junit.Test;

public class RiskNormalizePhoneTest {

  @Test
  public void evaluate() {
    RiskNormalizePhone riskNormalizePhone = new RiskNormalizePhone();
    Text text1 = riskNormalizePhone.evaluate(45L, new Text("|15900000000"), true);
    assertEquals("15900000000", text1.toString());
  }
}