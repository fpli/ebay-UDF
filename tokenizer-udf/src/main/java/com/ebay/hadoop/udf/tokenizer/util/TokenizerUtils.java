package com.ebay.hadoop.udf.tokenizer.util;

import com.ebay.hadoop.udf.tokenizer.TokenizerRef;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class TokenizerUtils {
  public static String IS_TESTING_KEY = "tokenizer.testing";

  public static int PHONE_FAX_MINIMUM_LENGTH = 6;

  public static boolean isTesting() {
    return System.getProperty(IS_TESTING_KEY) != null;
  }

  private static String padWithZeros(String data, int minLength) {
    if (null == data) return null;
    int zerosToAdd = minLength - data.length();
    if (zerosToAdd <= 0) {
      return data;
    } else {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < zerosToAdd; i++) {
        sb.append("0");
      }
      sb.append(data);
      return sb.toString();
    }
  }

  public static String filterData(TokenizerRef tokenizerRef, String data) {
    if (StringUtils.isBlank(data)) return data;

    switch (tokenizerRef) {
      case PHONE_FAX_HOME_TW:
        data = data.replaceAll("[^0-9()+-]", "");
        if (StringUtils.isNotBlank(data)) {
          data = padWithZeros(data, PHONE_FAX_MINIMUM_LENGTH);
        }
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
