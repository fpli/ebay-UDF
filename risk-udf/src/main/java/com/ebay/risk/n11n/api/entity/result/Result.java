package com.ebay.risk.n11n.api.entity.result;

import com.ebay.risk.normalize.enums.DataTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
    property = "dataType"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AddressResult.class, name = "ADDRESS"),
    @JsonSubTypes.Type(value = EmailResult.class, name = "EMAIL"),
    @JsonSubTypes.Type(value = PhoneResult.class, name = "PHONE")
})
public abstract class Result {

  protected DataTypeEnum dataType;

  @JsonIgnore
  public DataTypeEnum getDataType() {
    return dataType;
  }

  public void setDataType(DataTypeEnum dataType) {
    this.dataType = dataType;
  }

}
