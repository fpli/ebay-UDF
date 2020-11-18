package com.ebay.risk.normalize;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(builderMethodName = "phoneNRBuilder")
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PhoneNormalizationResult extends NormalizationResult{

  private int countryCodeE164;

  private String phoneNumberE164Format;
}
