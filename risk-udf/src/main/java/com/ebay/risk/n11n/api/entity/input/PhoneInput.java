package com.ebay.risk.n11n.api.entity.input;

import com.ebay.risk.n11n.data.AddressDataSerializeHelper;
import com.ebay.risk.normalize.enums.DataTypeEnum;
import java.util.Locale;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneInput extends Input {

  private String phoneNumber;

  // EBay legacy country id.
  private int countryId;

  public PhoneInput() {
    dataType = DataTypeEnum.PHONE;
  }

  public PhoneInput(String phoneNumber, int countryId) {
    this();
    this.phoneNumber = phoneNumber;
    this.countryId = countryId;
  }

  @Override
  public String getInputKey() {
    return dataType + ":" + countryId + AddressDataSerializeHelper.ADDR_SEP + phoneNumber;
  }

  @Override
  public Input convertToUniformedInput() {
    return new PhoneInput(phoneNumber.trim().toLowerCase(Locale.ENGLISH), countryId);
  }
}
