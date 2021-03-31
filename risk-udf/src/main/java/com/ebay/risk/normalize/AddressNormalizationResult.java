package com.ebay.risk.normalize;

import com.ebay.risk.n11n.api.entity.result.AddressResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(builderMethodName = "addressNRBuilder")
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class AddressNormalizationResult extends NormalizationResult {

  private AddressResult addressResult;

}
