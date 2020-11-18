package com.ebay.risk.n11n.api.entity.result;

import com.ebay.risk.n11n.data.AddressData;
import com.ebay.risk.n11n.data.Metadata;
import com.ebay.risk.normalize.enums.DataTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressResult extends Result {

  private AddressData address;

  private Metadata metadata = new Metadata();

  public AddressResult() {
    dataType = DataTypeEnum.ADDRESS;
  }

  public AddressResult(AddressData address, Metadata metadata) {
    this();
    this.address = address;
    if (metadata != null) {
      this.metadata = metadata;
    }
  }

  public AddressResult clone() {
    return new AddressResult(address.clone(), metadata.clone());
  }
}
