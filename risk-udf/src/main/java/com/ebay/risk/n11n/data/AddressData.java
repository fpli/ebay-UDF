package com.ebay.risk.n11n.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.function.Function;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressData {

  /**
   * For builder's ctor.
   */
  public AddressData(String addressLine, String locality, String province,
      String postalCode, String country, ProcessorMarks marks) {
    this.addressLine = addressLine;
    this.locality = locality;
    this.province = province;
    this.postalCode = postalCode;
    this.country = country;
    if (marks != null) {
      this.marks = marks;
    }
  }

  private String addressLine = "";
  /**
   * Usually city or area, could be empty.
   */
  private String locality = "";
  /**
   * Usually 2 letter US state name, could be empty.
   */
  private String province = "";
  private String postalCode = "";
  private String country = "";

  public void transformFiveFields(Function<String, String> transformer) {
    addressLine = transformer.apply(addressLine);
    locality = transformer.apply(locality);
    province = transformer.apply(province);
    postalCode = transformer.apply(postalCode);
    country = transformer.apply(country);
  }

  @JsonIgnore
  private ProcessorMarks marks = new ProcessorMarks();

  public AddressData clone() {
    AddressData addressData = this.toBuilder().build();
    addressData.setMarks(marks.clone());
    return addressData;
  }

}
