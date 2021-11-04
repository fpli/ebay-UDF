package com.ebay.search.qu

import org.apache.hadoop.hive.ql.exec.UDF
import com.ebay.hadoop.scalaplatform.helpers.SojHelpers

class Chunkify extends UDF {
  val sEmpty = new java.lang.String("")
  val sa0=new Array[java.lang.String](0)

  def normalize(s:java.lang.String, siteId:java.lang.Long):java.lang.String =
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


  def decode(s:java.lang.String):java.lang.String =
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


  def evaluate(s:java.lang.String, siteId:java.lang.Long, sz:java.lang.Integer):Array[java.lang.String] =
  {
    val s1 = normalize(decode(s), siteId)
    val sa=s1.split("\\s+").map(_.trim)

    if (sa.length < sz) {
      sa0
    }
    else {
      val saRet = new Array[java.lang.String](sa.length - sz + 1)
      var idx = 0
      for (idx <- 0 until sa.length - sz + 1) {
        val sb = new StringBuilder()
        var j = 0
        for (j <- idx until idx + sz) {
          if (j > idx) { sb.append(" ") }
          sb.append(sa(j))
        }

        saRet(idx) = sb.toString()
      }

      saRet
    }
  }
}
