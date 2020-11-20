package com.ebay.search.qu

import org.apache.commons.lang.StringUtils
import org.apache.hadoop.hive.ql.exec.UDF
import com.ebay.hadoop.scalaplatform.helpers.SojHelpers
import org.apache.commons.lang.StringUtils
import com.ebay.cassini.tokenizer.api.TokenizerArena

import scala.collection.JavaConversions._

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
