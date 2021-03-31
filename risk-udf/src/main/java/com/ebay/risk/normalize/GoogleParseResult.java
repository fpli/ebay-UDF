package com.ebay.risk.normalize;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoogleParseResult {

  private int countryCode;
  private String nationalNumber;
}
