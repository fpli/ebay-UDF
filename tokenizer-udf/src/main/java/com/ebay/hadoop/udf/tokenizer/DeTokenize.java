package com.ebay.hadoop.udf.tokenizer;

import com.ebay.platform.security.tokenizer.DetokenizeResponse;
import org.apache.hadoop.hive.ql.exec.UDF;

public class DeTokenize extends UDF {
  public String evaluate(String tokenizerRef, String token) {
    if (tokenizerRef == null || token == null) {
      return null;
    }
    DetokenizeResponse response =
        RetryableMicroVaultInstance.getSingleton().deTokenize(tokenizerRef, token);
    return response == null ? null : response.getData();
  }
}
