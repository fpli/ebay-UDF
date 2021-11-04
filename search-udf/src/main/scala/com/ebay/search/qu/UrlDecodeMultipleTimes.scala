package com.ebay.search.qu

import com.ebay.hadoop.scalaplatform.helpers.SojHelpers
import org.apache.hadoop.hive.ql.exec.UDF

class UrlDecodeMultipleTimes extends UDF {
  val sEmpty = new java.lang.String("")

  def evaluate(s:java.lang.String):java.lang.String =
  {
    try {
      SojHelpers.urlDecodeMultipleTimes(s)
    }
    catch {
      case e: Exception => {
        sEmpty
      }
    }
  }
}
