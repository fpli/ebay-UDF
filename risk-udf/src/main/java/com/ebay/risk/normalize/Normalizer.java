package com.ebay.risk.normalize;


import com.ebay.risk.exceptions.ClientException;
import com.ebay.risk.normalize.enums.DataTypeEnum;
import com.ebay.risk.normalize.enums.NormalizeStrategyEnum;

public interface Normalizer {
  NormalizationResult normalize(DataTypeEnum type, NormalizeStrategyEnum strategy, int version, String... keys) throws NormalizationException, ClientException;
  NormalizationResult normalize(DataTypeEnum type, String... keys) throws NormalizationException, ClientException;
}
