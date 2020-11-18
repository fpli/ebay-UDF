package com.ebay.risk.n11n.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ANSStructure {

  @JsonIgnore
  public int getAnsScore() {
    try {
      return Integer.parseInt(mailabilityScore);
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  private String number = "";
  private String street = "";
  private String subBuilding = "";
  private String building = "";
  private String organization = "";
  private String deliveryService = "";

  private String locality = "";
  private String province = "";
  private String postalCode = "";
  private String country = "";

  private String completeAddress = "";
  private String addressType = "";
  private String languageISO3 = "";
  private String mailabilityScore = "";
}
