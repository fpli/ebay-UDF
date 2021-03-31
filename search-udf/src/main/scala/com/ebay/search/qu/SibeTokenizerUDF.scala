package com.ebay.search.qu

import org.apache.commons.lang.StringUtils
import org.apache.hadoop.hive.ql.exec.UDF
import com.ebay.hadoop.scalaplatform.helpers.SojHelpers

class SibeTokenizerUDF extends UDF {
  val sEmpty = new java.lang.String("")
  def evaluate(s:java.lang.String, siteId:java.lang.Long):java.lang.String =
  {
    try {
      SojHelpers.stringNormalizedSIBEStyle(Option(s), siteId).get
    }
    catch {
      case e: Exception => {
        sEmpty
      }
    }
  }
}
