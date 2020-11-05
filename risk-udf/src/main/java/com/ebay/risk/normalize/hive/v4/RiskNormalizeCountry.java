package com.ebay.risk.normalize.hive.v4;

import com.ebay.risk.normalize.CountryNormalizer;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

public final class RiskNormalizeCountry extends UDF {

  private CountryNormalizer countryNormalizer = CountryNormalizer.getInstance();

  /**
   * @param rawCntry the input country of address
   */
  public String evaluate(final String rawCntry) {
    String fullCntry = countryNormalizer.normalize(rawCntry);
    if (!StringUtils.isBlank(fullCntry)) {
      return fullCntry;
    }
    return null;
  }

  /**
   * @param rawCntry the input country id of address
   */
  public String evaluate(final int rawCntry) {
    return evaluate(String.valueOf(rawCntry));
  }
}
