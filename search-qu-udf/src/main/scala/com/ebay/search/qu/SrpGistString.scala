package com.ebay.search.qu

import com.ebay.tracking._
import com.ebay.tracking.search._
import org.apache.hadoop.hive.ql.exec.UDF

import java.io._
import java.net.URLDecoder
import java.util.Base64

/** 
 * A UDF to turn a tracking SRP input into a serialized string.
 */ 
class SrpGistString extends UDF {
  lazy val srpDecoder = Decoders.srpGist

  val sEmpty = new java.lang.String("")

  def evaluate(s:java.lang.String, base64_flag:java.lang.Integer, url_decode_flag:java.lang.Integer):java.lang.String =
  {
    if (s == null || s.trim().length() == 0) {
      return sEmpty
    }

    var s1:java.lang.String = s

    if (url_decode_flag == 1) {
      try {
        s1 = URLDecoder.decode(s, "UTF-8")
      }
      catch {
        case e: Exception => {
          // best effort style
        }
      }
    }

    try {
      var srp: Srp= null
      if (base64_flag ==  1)  {
        try {
          val bytes = Base64.getDecoder.decode(s1)
          srp = srpDecoder.decode(bytes)
        }
        catch {
          case e: Exception => {
            // best effort style
            srp = srpDecoder.decode(s1.getBytes())
          }
        }
      }
      else {
        srp = srpDecoder.decode(s1.getBytes())
      }

      val result = srp.result

      result.toString()
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
