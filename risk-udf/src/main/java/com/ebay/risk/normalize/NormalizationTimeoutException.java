package com.ebay.risk.normalize;

import com.ebay.risk.exceptions.ClientException;

public class NormalizationTimeoutException extends ClientException {

  private static final long serialVersionUID = 7183179355031561514L;

  public NormalizationTimeoutException(Integer errcode, String errmsg, Object data) {
    super(errcode, errmsg, data);
  }
}
