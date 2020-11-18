package com.ebay.risk.n11n.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessorMarks {
  private boolean latestAnsResult;
  private String rawAddressLine = "";

  public ProcessorMarks clone() {
    return new ProcessorMarks(latestAnsResult, rawAddressLine);
  }
}
