package com.ebay.risk.normalize;
import com.ebay.risk.exceptions.ClientException;

public class NormalizationBadRequestException  extends ClientException{

  private static final long serialVersionUID = -5235834753385940895L;

  public NormalizationBadRequestException(String errmsg) {
    this(errmsg, null);
  }

  public NormalizationBadRequestException(String errmsg, Object data) {
    super(400, errmsg, data);
  }

}
