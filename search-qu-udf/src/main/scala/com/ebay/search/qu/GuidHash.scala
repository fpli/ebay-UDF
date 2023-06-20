package com.ebay.search.qu

import org.apache.hadoop.hive.ql.exec.UDF
import java.io._

import java.security.MessageDigest


/* This class is adapted from what Aritra extracted from Scala platform
 */ 
class GuidHash extends UDF {
  val sEmpty = new java.lang.String("")

  def evaluate(visitorId:java.lang.String, salt:java.lang.String, constant:java.lang.String):java.lang.String =
  {
    if (visitorId == null || visitorId.trim().length() == 0) {
      return sEmpty
    }

    try {
      val ALGORITHM_MD5:String = "MD5"
      val ENCODING_CHARSET:String = "ISO-8859-1"
      var bigInteger:BigInt = null
      val md5:MessageDigest = MessageDigest.getInstance(ALGORITHM_MD5)
      val constants = List(visitorId,salt,constant)
      for (c <- constants) {
        if (c != null && (!c.trim.isEmpty)) {
          md5.update(c.getBytes(ENCODING_CHARSET))
        }
      }
      bigInteger = BigInt(1, md5.digest());
      val hashCode:Int = bigInteger.hashCode()
      (Math.abs(hashCode) %100).toString
    }
    catch {
      case e: Exception => {
        val sw = new StringWriter
        e.printStackTrace(new PrintWriter(sw))
        return sw.toString()
      }
    }
  }
}
