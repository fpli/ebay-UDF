package com.ebay.risk.n11n.api.entity.result;

import com.ebay.risk.normalize.enums.DataTypeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailResult extends Result {

  private String normalizedEmail;
  private String localPart;
  private String domainPart;
  private boolean isValidEmail;

  public EmailResult() {
    dataType = DataTypeEnum.EMAIL;
  }

  public EmailResult(String normalizedEmail, String localPart, String domainPart, boolean isValidEmail) {
    this();
    this.normalizedEmail = normalizedEmail;
    this.localPart = localPart;
    this.domainPart = domainPart;
    this.isValidEmail = isValidEmail;
  }
}
