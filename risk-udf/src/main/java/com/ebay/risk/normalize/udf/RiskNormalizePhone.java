package com.ebay.risk.normalize.udf;

import com.ebay.risk.exceptions.ClientException;
import com.ebay.risk.n11n.api.entity.NormalizationResult;
import com.ebay.risk.n11n.api.entity.input.PhoneInput;
import com.ebay.risk.n11n.api.entity.result.PhoneResult;
import com.ebay.risk.normalize.PhoneNormalizer;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public final class RiskNormalizePhone extends UDF {

  private PhoneNormalizer normalizer = new PhoneNormalizer();

  public Text evaluate(Long countryId, Text phoneNumber) {
    return evaluate(countryId, phoneNumber, true);
  }

  public Text evaluate(Long countryId, Text phoneNumber, boolean nullIfError) {
    if (countryId == null || phoneNumber == null) {
      return null;
    }
    PhoneInput input = PhoneInput.builder()
        .countryId(countryId.intValue())
        .phoneNumber(phoneNumber.toString())
        .build();
    NormalizationResult result;
    try {
      result = normalizer.normalize(input);
    } catch (ClientException e) {
      if (nullIfError) {
        return null;
      } else {
        return phoneNumber;
      }
    }
    return new Text(((PhoneResult) result.getResult()).getNationalNumber());
  }
}
