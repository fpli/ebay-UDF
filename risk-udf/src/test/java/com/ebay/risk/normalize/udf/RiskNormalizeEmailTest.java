package com.ebay.risk.normalize.udf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.hadoop.io.Text;
import org.junit.Test;

public class RiskNormalizeEmailTest {

  RiskNormalizeEmail riskNormalizeEmail = new RiskNormalizeEmail();

  @Test
  public void testNormalize() {
    assertCase("Abc@d.com", true, false, "abc@d.com");
    assertCase(".A.bc+akjdflds@gmail.com", true, false,"abc@gmail.com");
    assertCase(".A.bc+akjdflds@googLemail.com", true, false, "abc@gmail.com");
    assertCase(".A.bc+akjdflds@googLemail.jp", true, false, "abc@gmail.jp");
    assertCase(".A.bc+akjdflds@yahoo.cn", true, false, "abc@yahoo.cn");
    assertCase(".A.bc+akjdflds@Hotmail.cn", true, false, ".a.bc@hotmail.cn");
    assertCase(".A.bc+akjdflds@outLook.cn", true, false, ".a.bc@outlook.cn");
    assertCase(".A.bc+akjdflds@ig.com.br", true, false, ".a.bc@ig.com.br");
    assertCase(".A.bc+akjdflds@yandex.com", true, false, ".a.bc@yandex.com");
    assertCase(".A.bc+akjdflds@live.com", true, false, ".a.bc@live.com");
    assertCase(".A.bc+akjdflds@ya.ru", true, false, ".a.bc@ya.ru");
    assertCase(".A.bc+akjdflds@narod.ru", true, false, ".a.bc@narod.ru");
  }

  @Test
  public void testIfAnyNull() {
    assertCase(null, true, false,null);
  }

  @Test
  public void testUseApacheValidator() {
    assertCase("aaa@a.com", true, true, "aaa@a.com");
    assertCase(".aaa@a.com", true, true, null);
  }

  @Test
  public void testSomeInvalidCase() {
    // For emails whose format is invalid (missing @, or domain name is invalid)
    // when nullIfInvalid == true, return null
    assertCase(".A.bc+akjdflds@outLook", true, false, null);
    assertCase(".A.bc+akjdfldsoutLook", true, false, null);
    assertCase("@A.bc+akjdfldsoutLook", true, false, null);
    assertCase(" ", true, false, null);
    assertCase("@", true, false, null);

    // when nullIfInvalid == false, return its original value
    assertCase(".A.bc+akjdflds@outLook", false, false, ".A.bc+akjdflds@outLook");
    assertCase(".A.bc+akjdfldsoutLook", false, false, ".A.bc+akjdfldsoutLook");
    assertCase("@A.bc+akjdfldsoutLook", false, false, "@A.bc+akjdfldsoutLook");
    assertCase(" ", false, false, " ");
    assertCase("@", false, false, "@");
    // when nullIfInvalid == false and useApacheValidator, remain the invalid input.
    assertCase(".aaa@a.com", false, true, ".aaa@a.com");
  }

  private void assertCase(String email, boolean nullIfInvalid, boolean useApacheValidator, String expected) {
    Text result = riskNormalizeEmail.evaluate(email == null ? null : new Text(email), nullIfInvalid, useApacheValidator);
    if (expected == null) {
      assertNull(result);
    } else {
      assertEquals(new Text(expected), result);
    }
  }
}