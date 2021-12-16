package com.ebay.search.qu

import org.apache.hadoop.hive.ql.exec.UDF
import com.ebay.hadoop.scalaplatform.helpers.SojHelpers

class QueryTypeUDF extends UDF {
  /**
   * Returns true if the given query (KW) is an advanced query
   */
  def evaluate(s:java.lang.String):java.lang.Boolean =
  {
    try {
      SojHelpers.isAdvancedQuery(s)
    }
    catch {
      case e: Exception => {
        false
      }
    }
  }
}
