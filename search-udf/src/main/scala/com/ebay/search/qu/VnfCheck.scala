package com.ebay.search.qu

import org.apache.commons.lang.StringUtils
import org.apache.hadoop.hive.ql.exec.UDF
import com.ebay.hadoop.scalaplatform.helpers.SojHelpers
import com.ebay.searchscience.aspects.normalize.JsonRegexAspectNormalizer

class VnfCheck extends UDF {
  val defVal = new java.lang.String("0")
  def evaluate(tag:java.lang.String, k:java.lang.String, v:java.lang.String, decodeFirst:java.lang.Integer):java.lang.String =
  {
    try {
      VnfCache.getInstance(tag).contains(k, v, decodeFirst.intValue())
    }
    catch {
      case e: Exception => {
        defVal
      }
    }
  }
}

