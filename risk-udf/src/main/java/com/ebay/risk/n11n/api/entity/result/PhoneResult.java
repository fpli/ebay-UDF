package com.ebay.risk.n11n.api.entity.result;

import com.ebay.risk.normalize.enums.DataTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@JsonIgnoreProperties
@Data
@Builder
public class PhoneResult extends Result {

  public PhoneResult() {
    dataType = DataTypeEnum.PHONE;
  }

  public PhoneResult(int countryId, String nationalNumber, int countryCode,
      String phoneNumberE164Format) {
    this();
    this.countryId = countryId;
    this.nationalNumber = nationalNumber;
    this.countryCode = countryCode;
    this.phoneNumberE164Format = phoneNumberE164Format;
  }

  /**
   * EBay legacy country id
   */
  private int countryId;

  /**
   * The pure digit format of the national number. (doesn't include country code)
   */
  private String nationalNumber;

  /**
   * E164 country code Could be empty if unknown.
   */
  private int countryCode;

  /**
   * The standard e.164 format. Could be empty if unknown.
   */
  private String phoneNumberE164Format;

}
