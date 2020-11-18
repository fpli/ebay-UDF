package com.ebay.risk.n11n.api.entity.input;

import static com.ebay.risk.n11n.data.AddressDataSerializeHelper.serialize;

import com.ebay.risk.n11n.data.AddressData;
import com.ebay.risk.n11n.data.AddressDataSerializeHelper;
import com.ebay.risk.normalize.enums.DataTypeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressInput extends Input {

  private AddressData address;

  public AddressInput() {
    dataType = DataTypeEnum.ADDRESS;
  }

  public AddressInput(AddressData address) {
    this();
    this.address = address;
  }

  @Override
  public String getInputKey() {
    return dataType + ":" + serialize(address);
  }

  @Override
  public Input convertToUniformedInput() {
    // Copy address field
    AddressInput result = new AddressInput(address.toBuilder().build());
    result.getAddress().transformFiveFields(AddressDataSerializeHelper::uniformField);
    return result;
  }
}
