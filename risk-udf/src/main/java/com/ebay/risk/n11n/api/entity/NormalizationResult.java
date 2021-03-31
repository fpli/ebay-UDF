package com.ebay.risk.n11n.api.entity;

import com.ebay.risk.n11n.api.entity.result.Result;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NormalizationResult {
  protected boolean normalized;
  protected Result result;
}
