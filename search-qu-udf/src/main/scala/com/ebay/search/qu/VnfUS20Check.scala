package com.ebay.search.qu

import org.apache.hadoop.hive.ql.exec.UDF

class VnfUS20Check extends UDF {
  val defVal = new java.lang.String("0")
  def evaluate(k:java.lang.String, v:java.lang.String, decodeFirst:java.lang.Integer):java.lang.String =
  {
    try {
      VnfCache.getUsVnf20Instance().contains(k, v, decodeFirst.intValue())
    }
    catch {
      case e: Exception => {
        defVal
      }
    }
  }
}
