package com.ebay.search.qu

import com.ebay.tracking._
import com.ebay.tracking.search._
import org.apache.hadoop.hive.ql.exec.UDF

import java.io._
import java.net.URLDecoder
import java.util.Base64

/* This class is adapted from https://github.corp.ebay.com/search-science/searchtracking/blob/master/src/test/scala/search/tracking/SrpGistException.scala
 */ 
class SrpGistException extends UDF {
  lazy val srpDecoder = Decoders.srpGist

  // def bytesToHex(bytes: Array[Byte]): String = bytes.map(String.format("%02X", java.lang.Byte.valueOf(_))).mkString(" ")

  def bytesToHex(bytes : Array[Byte]) = bytes.map{
    b => String.format("%02X", new java.lang.Integer(b & 0xff))
  }.mkString


  val sEmpty = new java.lang.String("")

  def evaluate(s:java.lang.String, base64_flag:java.lang.Integer, url_decode_flag:java.lang.Integer):java.lang.String =
  {
    if (s == null || s.trim().length() == 0) {
      return sEmpty
    }

    var s1:java.lang.String = s

    if (url_decode_flag == 1) {
      s1 = URLDecoder.decode(s, "UTF-8")
    }

    try {
      var srp: Srp= null
      if (base64_flag ==  1)  {
        val bytes = Base64.getDecoder.decode(s1)
        srp = srpDecoder.decode(bytes)
      }
      else {
        srp = srpDecoder.decode(s1.getBytes())
      }

      val result = srp.result
      val b = result.isNull 

      sEmpty
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
