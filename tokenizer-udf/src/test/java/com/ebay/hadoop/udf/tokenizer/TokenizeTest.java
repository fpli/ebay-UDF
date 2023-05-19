package com.ebay.hadoop.udf.tokenizer;

import org.junit.Test;

public class TokenizeTest extends AbstractTokenizerTest {
  @Test(expected = TokenizerException.class)
  public void testTokenize() {
    Tokenize tokenize = new Tokenize();
    tokenize.evaluate("email-tokenizer-2way", "iuyjhue@kru.khe");
  }
}
