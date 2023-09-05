package com.ebay.hadoop.udf.tokenizer;

import com.ebay.platform.security.tokenizer.DetokenizeResponse;
import com.ebay.platform.security.tokenizer.TokenizeResponse;
import com.ebay.platform.security.tokenizer.TokenizerMetadata;
import java.util.List;

public interface IMicroVaultInstance {
  /** message TokenizeResponse { string token = 1; string keyVersion = 2; bool keyEmbedded = 3; } */
  TokenizeResponse tokenize(String tokenizerRef, String data);

  List<TokenizeResponse> batchTokenize(String tokenizerRef, List<String> dataList);

  /** message DetokenizeResponse { string data = 1; } */
  DetokenizeResponse deTokenize(String tokenizerRef, String token);

  List<DetokenizeResponse> batchDeTokenize(String tokenizerRef, List<String> tokenList);

  /**
   * message TokenizerMetadata { string name = 1; string id = 2; bool reversible = 3; string
   * description = 4; }
   */
  List<TokenizerMetadata> findTokenizerMetadata(
      String storeType, String storeName, String tableName, String columnName);
}
