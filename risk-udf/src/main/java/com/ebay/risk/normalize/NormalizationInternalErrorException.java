package com.ebay.risk.normalize;

import com.ebay.risk.exceptions.ClientException;

public class NormalizationInternalErrorException extends ClientException {

  private static final long serialVersionUID = 3476248542817272716L;

  public NormalizationInternalErrorException(Integer errcode, String errmsg, Object data) {
    super(errcode, errmsg, data);
  }
}
