package com.ebay.hadoop.udf.tokenizer;

import com.ebay.platform.security.tokenizer.TokenizeResponse;
import org.apache.hadoop.hive.ql.exec.UDF;

public class Tokenize extends UDF {
  public String evaluate(String tokenizerRef, String data) {
    if (tokenizerRef == null || data == null) {
      return null;
    }
    TokenizeResponse response =
        RetryableMicroVaultInstance.getSingleton().tokenize(tokenizerRef, data);
    return response == null ? null : response.getToken();
  }
}
