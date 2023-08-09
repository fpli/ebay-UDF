package com.ebay.hadoop.udf.tokenizer;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.hadoop.hive.ql.exec.UDF;

public class BatchTokenize extends UDF {
  public List<String> evaluate(String tokenizerRef, List<String> dataList) {
    if (tokenizerRef == null || dataList == null) {
      return null;
    }

    if (dataList.isEmpty()) return Collections.emptyList();

    return MicroVaultInstance.getSingleton().batchTokenizeWithRetry(tokenizerRef, dataList).stream()
        .map(
            response -> {
              return response == null ? null : response.getToken();
            })
        .collect(Collectors.toList());
  }
}
