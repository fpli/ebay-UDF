package com.ebay.risk.normalize;

public enum N11nExceptionCodeEnum {

  PHONEVERSION(1001, "Phone normalization version or strategy error"),
  EMAILVERSION(1002, "Email normalization version or strategy error"),
  ADDRESSVERSION(1003, "Address normalization version or strategy error"),
  ADDRESSANS(1004, "ANS is unavailable");

  private final int id;
  private final String message;

  N11nExceptionCodeEnum(int id, String message) {
    this.id = id;
    this.message = message;
  }

  public int id() {
    return id;
  }


  public String message() {
    return message;
  }
}
