package com.ebay.hadoop.udf.tokenizer;

import com.ebay.hadoop.udf.tokenizer.util.TokenizerUtils;

abstract class AbstractTokenizerTest {
  static {
    System.setProperty(TokenizerUtils.IS_TESTING_KEY, "true");
  }
}
