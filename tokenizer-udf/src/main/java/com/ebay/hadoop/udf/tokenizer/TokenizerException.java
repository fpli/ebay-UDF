package com.ebay.hadoop.udf.tokenizer;

public class TokenizerException extends RuntimeException {
  public TokenizerException(String message, Throwable cause) {
    super(message, cause);
  }

  public TokenizerException(String message) {
    this(message, null);
  }

  public TokenizerException(Throwable cause) {
    this(null, cause);
  }
}
