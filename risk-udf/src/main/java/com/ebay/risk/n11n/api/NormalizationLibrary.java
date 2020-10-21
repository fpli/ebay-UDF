package com.ebay.risk.n11n.api;

import com.ebay.risk.exceptions.ClientException;
import com.ebay.risk.n11n.api.entity.NormalizationResult;
import com.ebay.risk.n11n.api.entity.input.Input;

/**
 * Please refer to https://github.corp.ebay.com/qinling/normalization#spring-beans-to-use to get
 * detailed information for each concrete sub-class.
 */
public interface NormalizationLibrary {

  /**
   * Normalize the input and return the normalized result.
   * @param input the input object to normalize.
   * @return the output object as normalized result.
   * @throws ClientException if errcode == 4xx, some input field is invalid or missing. Or else is
   *                         the library/service's internal error.
   */
  NormalizationResult normalize(Input input) throws ClientException;
}
