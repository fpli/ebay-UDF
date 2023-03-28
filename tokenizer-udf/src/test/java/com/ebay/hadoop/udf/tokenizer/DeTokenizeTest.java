package com.ebay.hadoop.udf.tokenizer;

import org.junit.Test;

public class DeTokenizeTest {
  @Test(expected = TokenizerException.class)
  public void testDeTokenize() {
    DeTokenize deTokenize = new DeTokenize();
    deTokenize.evaluate("email-tokenizer-2way", "token");
  }
}
