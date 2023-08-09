package com.ebay.hadoop.udf.tokenizer.util;

import com.ebay.hadoop.udf.tokenizer.TokenizerRef;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class TokenizerUtils {
  public static String IS_TESTING_KEY = "tokenizer.testing";

  public static boolean isTesting() {
    return System.getProperty(IS_TESTING_KEY) != null;
  }

  public static String filterData(TokenizerRef tokenizerRef, String data) {
    if (StringUtils.isBlank(data)) return data;

    switch (tokenizerRef) {
      case PHONE_FAX_HOME_TW:
        data = data.replaceAll("[^0-9()+-]", "");
        break;
      default:
    }
    return data;
  }

  public static List<String> filterData(TokenizerRef tokenizerRef, List<String> dataList) {
    return dataList.stream()
        .map(data -> filterData(tokenizerRef, data))
        .collect(Collectors.toList());
  }
}
