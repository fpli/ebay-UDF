package com.ebay.hadoop.udf.tokenizer.util;

import com.ebay.hadoop.udf.tokenizer.TokenizerRef;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class TokenizerUtils {
  public static String IS_TESTING_KEY = "tokenizer.testing";

  public static int PHONE_FAX_DIGIT_MINIMUM_LENGTH = 6;

  public static boolean isTesting() {
    return System.getProperty(IS_TESTING_KEY) != null;
  }

  /**
   * Try to pad the input data with zeros. It will check the digit number in the data first, 1. if
   * no digit in the input data, return null. 2. if the digit length is not less than the minimum
   * digit length, return data. 3. otherwise, pad the zeros before the first digit in the data.
   */
  private static String padDigitWithZeros(String data, int minDigitLength) {
    if (null == data) return null;

    int digitNumber = 0;
    int firstDigitIndex = -1;

    for (int i = 0; i < data.length(); i++) {
      if (Character.isDigit(data.charAt(i))) {
        if (firstDigitIndex < 0) {
          firstDigitIndex = i;
        }
        digitNumber++;
        if (digitNumber >= minDigitLength) {
          break;
        }
      }
    }

    if (digitNumber == 0) {
      return null;
    } else {
      int zerosToAdd = minDigitLength - digitNumber;
      if (zerosToAdd <= 0) {
        return data;
      } else {
        StringBuilder sb = new StringBuilder();
        sb.append(data, 0, firstDigitIndex);
        for (int i = 0; i < zerosToAdd; i++) {
          sb.append("0");
        }
        sb.append(data, firstDigitIndex, data.length());
        return sb.toString();
      }
    }
  }

  public static String filterData(TokenizerRef tokenizerRef, String data) {
    if (StringUtils.isBlank(data)) return data;

    switch (tokenizerRef) {
      case PHONE_FAX_HOME_TW:
        data = data.replaceAll("[^0-9()+-]", "");
        if (StringUtils.isNotBlank(data)) {
          data = padDigitWithZeros(data, PHONE_FAX_DIGIT_MINIMUM_LENGTH);
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

  public static String stringifyException(Throwable e) {
    StringWriter stm = new StringWriter();
    PrintWriter wrt = new PrintWriter(stm);
    e.printStackTrace(wrt);
    wrt.close();
    return stm.toString();
  }
}
