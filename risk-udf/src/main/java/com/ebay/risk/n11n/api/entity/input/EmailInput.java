package com.ebay.risk.n11n.api.entity.input;

import com.ebay.risk.normalize.enums.DataTypeEnum;
import java.util.Locale;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailInput extends Input {

  private String email;

  public EmailInput() {
    dataType = DataTypeEnum.EMAIL;
  }

  public EmailInput(String email) {
    this();
    this.email = email;
  }

  @Override
  public String getInputKey() {
    return dataType + ":" + email;
  }

  @Override
  public Input convertToUniformedInput() {
    return new EmailInput(email.trim().toLowerCase(Locale.ENGLISH));
  }
}
