package com.ebay.risk.normalize;

import com.ebay.risk.exceptions.ClientException;
import com.ebay.risk.n11n.api.entity.input.EmailInput;
import com.ebay.risk.n11n.api.entity.result.EmailResult;
import com.ebay.risk.normalize.enums.NormalizeStrategyEnum;
import org.junit.Assert;
import org.junit.Test;

public class EmailNormalizerTest {

  private final EmailNormalizer emailNormalizer = new EmailNormalizer();

  @Test
  public void testOther() throws ClientException {
    assertLine("Abc@d.com", "abc@d.com");
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
    Assert.assertEquals(true, emailResult.isValidInput());
  }

  private void assertLine(String input, String expected) throws ClientException {
    Assert.assertTrue(
        emailNormalizer.normalize(NormalizeStrategyEnum.DEFAULT, 1, input).getNormalizedValue()
            .equals(expected));
  }

  @Test(expected = ClientException.class)
  public void testMultiPlus() throws ClientException {
    String source = "+++@gmail.com";
    String normalizedRet = emailNormalizer.normalize(NormalizeStrategyEnum.DEFAULT, 1, source)
        .getNormalizedValue();
    Assert.assertEquals(normalizedRet.equals("@gmail.com"), true);
  }
}
