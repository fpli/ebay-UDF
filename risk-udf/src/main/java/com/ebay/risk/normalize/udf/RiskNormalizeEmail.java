package com.ebay.risk.normalize.udf;

import com.ebay.risk.exceptions.ClientException;
import com.ebay.risk.n11n.api.entity.NormalizationResult;
import com.ebay.risk.n11n.api.entity.input.EmailInput;
import com.ebay.risk.n11n.api.entity.result.EmailResult;
import com.ebay.risk.normalize.EmailNormalizer;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public final class RiskNormalizeEmail extends UDF {

  private EmailNormalizer normalizer = new EmailNormalizer();

  public Text evaluate(final Text s) {
    return evaluate(s, true, false);
  }

  /**
   * @param s the email address
   */
  public Text evaluate(final Text s, final boolean nullIfInvalid, final boolean useApacheValidator) {
    if (s == null) {
      return null;
    }
    EmailInput input = EmailInput.builder().email(s.toString()).build();
    NormalizationResult result;
    try {
      result = normalizer.normalize(input);
    } catch (ClientException e) {
      if (nullIfInvalid) {
        return null;
      } else {
        return s;
      }
    }
    EmailResult emailResult = (EmailResult) result.getResult();
    if (useApacheValidator && !emailResult.isValidInput()) {
      return nullIfInvalid ? null : s;
    }
    return new Text(((EmailResult) result.getResult()).getNormalizedEmail());
  }
}
