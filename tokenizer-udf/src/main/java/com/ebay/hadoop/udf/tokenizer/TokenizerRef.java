package com.ebay.hadoop.udf.tokenizer;

import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

public enum TokenizerRef {
  PHONE_FAX_HOME_TW,
  OTHER_TOKENIZER_REF;

  public static TokenizerRef apply(String refName) {
    if (StringUtils.isBlank(refName)) return OTHER_TOKENIZER_REF;

    TokenizerRef tokenizerRef;
    switch (refName.trim().toLowerCase(Locale.ROOT)) {
      case "phone-fax-home-tw":
        tokenizerRef = PHONE_FAX_HOME_TW;
        break;
      default:
        tokenizerRef = OTHER_TOKENIZER_REF;
    }
    return tokenizerRef;
  }
}
