package com.ebay.risk.normalize;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.ebay.risk.exceptions.ClientException;
import com.ebay.risk.n11n.api.entity.input.EmailInput;
import com.ebay.risk.n11n.api.entity.result.EmailResult;
import com.ebay.risk.normalize.enums.NormalizeStrategyEnum;
import org.apache.commons.validator.EmailValidator;
import org.junit.Assert;
import org.junit.Test;

public class EmailNormalizerTest {

  private final EmailNormalizer emailNormalizer = new EmailNormalizer();
  private EmailValidator validator = EmailValidator.getInstance();

  @Test
  public void testOther() throws ClientException {
    assertLine("Abc@d.com", "abc@d.com");

    assertFalse(validator.isValid("\\aaaaa@lycosmail.com"));
    assertFalse(validator.isValid("mail\\pmachaterre@productaction.com"));

    assertTrue(validator.isValid("+++@gmail.com"));

    assertFalse(validator.isValid("w.d.@lycosmail.com"));
    assertFalse(validator.isValid("www.dlonestar@78"));
  }

  @Test
  public void testGmail() throws ClientException {
    assertLine(".A.bc+akjdflds@gmail.com", "abc@gmail.com");
  }

  @Test
  public void testGooglemail() throws ClientException {
    assertLine(".A.bc+akjdflds@googLemail.com", "abc@gmail.com");
  }

  @Test
  public void testGooglemailjp() throws ClientException {
    assertLine(".A.bc+akjdflds@googLemail.jp", "abc@gmail.jp");
  }

  @Test
  public void testYahoo() throws ClientException {
    assertLine(".A.bc+akjdflds@yahoo.cn", "abc@yahoo.cn");
  }

  @Test
  public void testHotmail() throws ClientException {
    assertLine(".A.bc+akjdflds@Hotmail.cn", ".a.bc@hotmail.cn");
  }

  @Test
  public void testOutlook() throws ClientException {
    assertLine(".A.bc+akjdflds@outLook.cn", ".a.bc@outlook.cn");
  }

  @Test
  public void testIg() throws ClientException {
    assertLine(".A.bc+akjdflds@ig.com.br", ".a.bc@ig.com.br");
  }

  @Test
  public void testYandex() throws ClientException {
    assertLine(".A.bc+akjdflds@yandex.com", ".a.bc@yandex.com");
  }

  @Test
  public void testLive() throws ClientException {
    assertLine(".A.bc+akjdflds@live.com", ".a.bc@live.com");
  }

  @Test
  public void testYa() throws ClientException {
    assertLine(".A.bc+akjdflds@ya.ru", ".a.bc@ya.ru");
  }

  @Test
  public void testNarod() throws ClientException {
    assertLine(".A.bc+akjdflds@narod.ru", ".a.bc@narod.ru");
    // Yandex for all top domains
    assertLine(".A.bc+akjdflds@yandex.xxx", ".a.bc@yandex.xxx");
    assertLine(".A.bc+akjdflds@yandex.ru", ".a.bc@yandex.ru");
    // ig for only .com.br
    assertLine(".A.bc+akjdflds@ig.com.br", ".a.bc@ig.com.br");
    assertLine(".A.bc+akjdflds@ig.xx", ".a.bc+akjdflds@ig.xx");
  }

  @Test(expected = ClientException.class)
  public void testInvalidDomain() throws ClientException {
    String source = ".A.bc+akjdflds@outLook";
    emailNormalizer.normalize(NormalizeStrategyEnum.DEFAULT, 1, source).getNormalizedValue();
  }

  @Test(expected = ClientException.class)
  public void testInvalidEmail() throws ClientException {
    String source = ".A.bc+akjdfldsoutLook";
    emailNormalizer.normalize(NormalizeStrategyEnum.DEFAULT, 1, source).getNormalizedValue();
  }

  @Test(expected = ClientException.class)
  public void testInvalidEmailID() throws ClientException {
    String source = "@A.bc+akjdfldsoutLook";
    emailNormalizer.normalize(NormalizeStrategyEnum.DEFAULT, 1, source).getNormalizedValue();
  }

  @Test(expected = ClientException.class)
  public void testEmptyEmail() throws ClientException {
    String source = " ";
    emailNormalizer.normalize(NormalizeStrategyEnum.DEFAULT, 1, source).getNormalizedValue();
  }

  @Test(expected = ClientException.class)
  public void testDiffenentVersion() throws ClientException {
    String source = ".A.bc+akjdflds@Hotmail.cn";
    emailNormalizer.normalize(NormalizeStrategyEnum.DEFAULT, 0, source);
  }

  @Test(expected = ClientException.class)
  public void testSingleAt() throws ClientException {
    String source = "@";
    emailNormalizer.normalize(NormalizeStrategyEnum.DEFAULT, 1, source).getNormalizedValue();
  }

  @Test
  public void testRemoveTabSingal() throws ClientException {
    String source = "33.\tgjg\tfjfgj_7@hotmail.com";
    String normalizedRet = emailNormalizer.normalize(NormalizeStrategyEnum.DEFAULT, 1, source)
        .getNormalizedValue();
    Assert.assertEquals(normalizedRet.equals("33.gjgfjfgj_7@hotmail.com"), true);
  }

  @Test
  public void testRemoveSpaceSingal() throws ClientException {
    String source = "33. gjgfj fg j_7@hotmail.com";
    String normalizedRet = emailNormalizer.normalize(NormalizeStrategyEnum.DEFAULT, 1, source)
        .getNormalizedValue();
    Assert.assertEquals(normalizedRet.equals("33.gjgfjfgj_7@hotmail.com"), true);
  }

  @Test
  public void testValidInput() throws ClientException {
    String source = "1123@qq.com";
    EmailInput emailInput = EmailInput.builder().email(source).build();
    com.ebay.risk.n11n.api.entity.NormalizationResult normalizationResult = emailNormalizer
        .normalize(emailInput);
    EmailResult emailResult = (EmailResult) normalizationResult.getResult();
    Assert.assertEquals(true, emailResult.getNormalizedEmail().equals("1123@qq.com"));
    Assert.assertEquals(true, emailResult.isValidEmail());
  }

  private void assertLine(String input, String expected) throws ClientException {
    assertEquals(expected,
        emailNormalizer.normalize(NormalizeStrategyEnum.DEFAULT, 1, input).getNormalizedValue());
  }

  @Test(expected = ClientException.class)
  public void testMultiPlus() throws ClientException {
    String source = "+++@gmail.com";
    String normalizedRet = emailNormalizer.normalize(NormalizeStrategyEnum.DEFAULT, 1, source)
        .getNormalizedValue();
    Assert.assertEquals(normalizedRet.equals("@gmail.com"), true);
  }

  //QE P1 test case:https://wiki.vip.corp.ebay.com/pages/editpage.action?pageId=801927358
  @Test
  public void testP1Case() throws ClientException {
    assertEmailResult("jincai.1+test@googlemail.com", "jincai1@gmail.com");
    assertEmailResult("jincai.1@test.deleted", "jincai.1@test.deleted");
    assertEmailResult("jincai.1+test1+test2@ig.com.br", "jincai.1@ig.com.br");
    assertEmailResult("jincai.1+test+test@ig.com.br.com", "jincai.1+test+test@ig.com.br.com");
    assertEmailResult("jincai.1+test1+test2@yandex.com", "jincai.1@yandex.com");
    assertEmailResult("jincai.1+test1+test2@yandex.hk.com", "jincai.1@yandex.hk.com");
    assertEmailResult("jincai.1+test1+test2@abc.yandex.hk.com",
        "jincai.1+test1+test2@abc.yandex.hk.com");
    assertEmailResult("jincai.1+test1+test2@live.com", "jincai.1@live.com");
    assertEmailResult("jincai.1+test1+test2@live.hk.com", "jincai.1@live.hk.com");
    assertEmailResult("jincai.1+test1+test2@live.com.cn", "jincai.1@live.com.cn");
    assertEmailResult("jincai.1+test1+test2@ya.ru", "jincai.1@ya.ru");
    assertEmailResult("jincai.1+test1+test2@narod.ru", "jincai.1@narod.ru");
    assertEmailResult("jincai.1+test1+test2@narod.ru.com", "jincai.1+test1+test2@narod.ru.com");
    assertEmailResult("jincai.1+test1+test2@narod.com", "jincai.1+test1+test2@narod.com");
  }

  private void assertEmailResult(String input, String expected) throws ClientException {
    EmailInput emailInput = EmailInput.builder().email(input).build();
    assertEquals(expected,
        ((EmailResult) emailNormalizer.normalize(emailInput).getResult()).getNormalizedEmail());
  }
}
