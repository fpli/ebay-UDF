package com.ebay.hadoop.udf.tokenizer;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.hadoop.hive.ql.exec.UDF;

public class BatchDeTokenize extends UDF {
  public List<String> evaluate(String tokenizerRef, List<String> tokenList) {
    if (tokenizerRef == null || tokenList == null) {
      return null;
    }

    if (tokenList.isEmpty()) return Collections.emptyList();

    return MicroVaultInstance.getSingleton().batchDeTokenizeWithRetry(tokenizerRef, tokenList)
        .stream()
        .map(
            response -> {
              return response.getData();
            })
        .collect(Collectors.toList());
  }
}
