package com.ebay.hadoop.udf.tokenizer.util;

import com.ebay.hadoop.udf.tokenizer.TokenizerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonUtils {
  private static ObjectMapper objectMapper = new ObjectMapper();

  public static <T> String toJson(T object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (IOException e) {
      throw new TokenizerException(
          String.format("Failed to convert object(%s) to json", object), e);
    }
  }
}
