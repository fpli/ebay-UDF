package com.ebay.risk.normalize.hive.v4;

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
    return evaluate(countryId, phoneNumber, true, -1);
  }

  public Text evaluate(Long countryId, Text phoneNumber, boolean nullIfInvalid, int minimumLength) {
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
      if (nullIfInvalid) {
        return null;
      } else {
        return phoneNumber;
      }
    }
    PhoneResult phoneResult = (PhoneResult) result.getResult();
    if (minimumLength > 0) {
      int normalizedLength = phoneResult.getNationalNumber().length();
      if (normalizedLength < minimumLength) {
        return nullIfInvalid ? null : phoneNumber;
      }
    }
    return new Text(((PhoneResult) result.getResult()).getNationalNumber());
  }
}
