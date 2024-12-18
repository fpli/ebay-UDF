package com.ebay.search.qu

import org.apache.hadoop.hive.ql.exec.UDF

class FireflyUDF extends UDF {
  val sEmpty = new java.lang.String("")
  def evaluate(s:java.lang.String, pos:java.lang.Integer, decode:java.lang.Integer):java.lang.String =
  {
    try {
      return FireflyParser.getQuery(s, pos)
    }
    catch {
      case e: Exception => {
        sEmpty
      }
    }
  }
}
