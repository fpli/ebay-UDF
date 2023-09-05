package com.ebay.hadoop.udf.tokenizer;

import com.ebay.hadoop.udf.tokenizer.util.JsonUtils;
import com.ebay.platform.security.tokenizer.TokenizerMetadata;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.hadoop.hive.ql.exec.UDF;

public class FindTokenizerMetadata extends UDF {
  public List<String> evaluate(
      String storeType, String storeName, String tableName, String columnName) {
    if (storeType == null || storeName == null || tableName == null || columnName == null) {
      return null;
    }
    List<TokenizerMetadata> metadataList =
        RetryableMicroVaultInstance.getSingleton()
            .findTokenizerMetadata(storeType, storeName, tableName, columnName);
    return metadataList.stream()
        .map(
            metadata -> {
              return tokenizerMetadataToString(metadata);
            })
        .collect(Collectors.toList());
  }

  /** { string name = 1; string id = 2; bool reversible = 3; string description = 4; } */
  static String tokenizerMetadataToString(TokenizerMetadata metadata) {
    Map<String, Object> map = new HashMap<>();
    map.put("name", metadata.getName());
    map.put("id", metadata.getId());
    map.put("reversible", metadata.getReversible());
    map.put("description", metadata.getDescription());
    return JsonUtils.toJson(map);
  }
}
