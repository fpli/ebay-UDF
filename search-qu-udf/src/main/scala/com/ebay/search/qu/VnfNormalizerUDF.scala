package com.ebay.search.qu

import com.ebay.searchscience.aspects.normalize.JsonRegexAspectNormalizer
import org.apache.hadoop.hive.ql.exec.UDF

class VnfNormalizerUDF extends UDF {
  val sEmpty = new java.lang.String("")
  def evaluate(k:java.lang.String, v:java.lang.String, siteId:java.lang.Long, catId:java.lang.Long):java.lang.String =
  {
    try {
      JsonRegexAspectNormalizer.normalizeAspectText(v, siteId, catId, k)
    }
    catch {
      case e: Exception => {
        sEmpty
      }
    }
  }
}

