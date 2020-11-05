package com.ebay.risk.exceptions;

public class RetryableException extends ClientException {

  private static final long serialVersionUID = -6061319162818143746L;

  public RetryableException(Integer errcode, String errmsg, Object data) {
    super(errcode, errmsg, data);
  }
  public RetryableException(Integer errcode, String errmsg, Object data, Throwable error) {
    super(errcode, errmsg, data, error);
  }
}
