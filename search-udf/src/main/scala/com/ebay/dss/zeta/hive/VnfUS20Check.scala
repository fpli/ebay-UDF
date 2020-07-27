package com.ebay.dss.zeta.hive

import org.apache.commons.lang.StringUtils
import org.apache.hadoop.hive.ql.exec.UDF
import com.ebay.hadoop.scalaplatform.helpers.SojHelpers
import com.ebay.searchscience.aspects.normalize.JsonRegexAspectNormalizer
import com.ebay.dss.zeta.hive.VnfCache

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

