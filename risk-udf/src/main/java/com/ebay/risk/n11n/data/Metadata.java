package com.ebay.risk.n11n.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;
import java.util.TreeMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata {
  public static final String SCORE_FIRST_ANS_SCORE = "firstANSScore";
  public static final String SCORE_LAST_ANS_SCORE = "lastANSScore";
  public static final int LEVEL_NOT_NORMALIZED = 0;
  public static final int LEVEL_JUST_ANS = 1;
  public static final int LEVEL_FULLY_NORMALIZED = 2;

  // 0: not normalized
  // 1: just ANS
  // 2: fully normalized
  private int normalizationLevel = LEVEL_NOT_NORMALIZED;
  // many scores
  private Map<String, Double> scores = new TreeMap<>();
  // The ANS output and its structure
  private ANSStructure ansStructure = new ANSStructure();
  private boolean isPOBox = false;
  // Record how many times calling ANS for this address
  private int callANSCount = 0;

  public Metadata clone() {
    return Metadata.builder()
        .scores(new TreeMap<>(scores))
        .ansStructure(ansStructure.toBuilder().build())
        .isPOBox(isPOBox)
        .callANSCount(callANSCount)
        .build();
  }
}
