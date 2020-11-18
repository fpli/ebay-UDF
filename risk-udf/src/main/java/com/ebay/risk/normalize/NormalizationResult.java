package com.ebay.risk.normalize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NormalizationResult {

  protected boolean valid;
  protected boolean normalized;
  protected String normalizedValue;

}
