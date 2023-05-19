package com.ebay.hadoop.udf.tokenizer.util;

public class TokenizerUtils {
  public static String IS_TESTING_KEY = "tokenizer.testing";

  public static boolean isTesting() {
    return System.getProperty(IS_TESTING_KEY) != null;
  }
}
