package com.ebay.search.qu

import java.util.Base64
import java.io._

import java.net.{URI, URLDecoder}

import com.ebay.tracking.search._
import com.ebay.tracking._
import org.apache.avro.SchemaNormalization
import org.apache.avro.message.{BinaryMessageDecoder, BinaryMessageEncoder}
import org.apache.avro.specific.SpecificData

import org.apache.commons.lang.StringUtils
import org.apache.hadoop.hive.ql.exec.UDF
import com.ebay.hadoop.scalaplatform.helpers.SojHelpers
import org.apache.commons.lang.StringUtils
import com.ebay.cassini.tokenizer.api.TokenizerArena
import com.ebay.hadoop.scalaplatform.spark._

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