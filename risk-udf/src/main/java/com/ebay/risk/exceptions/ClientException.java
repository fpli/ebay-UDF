package com.ebay.risk.exceptions;

public class ClientException extends Exception{

  private static final long serialVersionUID = -8844010576694803841L;

  private final Integer errcode;

  private final String errmsg;

  private final transient Object data;

  public ClientException(Integer errcode, String errmsg, Object data, Throwable error) {
    super("errcode=" + errcode + "&errmsg=" + errmsg, error);
    this.errcode = errcode;
    this.errmsg = errmsg;
    this.data = data;
  }

  public ClientException(Integer errcode, String errmsg, Object data) {
    super("errcode=" + errcode + "&errmsg=" + errmsg);
    this.errcode = errcode;
    this.errmsg = errmsg;
    this.data = data;
  }

  public Integer getErrcode() {
    return errcode;
  }

  public String getErrmsg() {
    return errmsg;
  }

  public Object getData() {
    return data;
  }
}
