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

/* This class is adapted from https://github.corp.ebay.com/search-science/searchtracking/blob/master/src/test/scala/search/tracking/SrpGistException.scala
 */ 
class SrpGistException extends UDF {
  lazy val srpDecoder = Decoders.srpGist

  def test2() {
    var encoded = "wwGnJNADX5smHgAAAgAAEDEyMzQ1Njc4EE9yaWdpbmFslKUE8JgEAAAKdJqY1qXEEADC/rvYtQ8AmNywvPwIALafsLz8CACum6fx1AYAtr/Ku/0PAIaG1fjvFgDOvOG8/Q8AkPXfrcQQAPqSq8D9DwCcktn83QoA5Ij4ockJAKj3vNu1DwD+vcXVjBEA7rey2Z0HAL6p5+TMDQDOwefkzA0ApJffpcQQAIqa8qjWEQDkofX7nRIAvLmqhZQKANjx7/qTCgDqxOPB8BIAgMmW28MQAMKIh4PqDgDEjNLOyAkA3o7ds54HAKrdzLaGDQDSpfzUtQ8Aosqjw/ASAIC504jqDgCGwp+XyQkA8sHqs4YNAN6Mj6vEEACMr9LM3goA0Ovw4ccUAJy6uPzvFgDGzJX+7xYA3PO94dQGAIKej/CCDQDUrKHJzQ0AiPjMgZ4SAK7aw9mTCgDQspirrwgAxsuR2ZAVALKw8sfeCgC41ILL3goAjMjv8J0SAKLPhLfdCgCYqYq9/Q8AvKbA6JMKALLk9/fvFgC21sekrwgAyN7Lsa8IALjnjojqDgCAx7GDnhIA7Mea7KILAOaF9KvEEAAAAA=="

    encoded = "wwGnJNADX5smHgAAAgAAEDEyMzQ1Njc4EE9yaWdpbmFslKUE8JgEAAAKdJqY1qXEEADC/rvYtQ8AmNywvPwIALafsLz8CACum6fx1AYAtr/Ku/0PAIaG1fjvFgDOvOG8/Q8AkPXfrcQQAPqSq8D9DwCcktn83QoA5Ij4ockJAKj3vNu1DwD+vcXVjBEA7rey2Z0HAL6p5+TMDQDOwefkzA0ApJffpcQQAIqa8qjWEQDkofX7nRIAvLmqhZQKANjx7/qTCgDqxOPB8BIAgMmW28MQAMKIh4PqDgDEjNLOyAkA3o7ds54HAKrdzLaGDQDSpfzUtQ8Aosqjw/ASAIC504jqDgCGwp+XyQkA8sHqs4YNAN6Mj6vEEACMr9LM3goA0Ovw4ccUAJy6uPzvFgDGzJX+7xYA3PO94dQGAIKej/CCDQDUrKHJzQ0AiPjMgZ4SAK7aw9mTCgDQspirrwgAxsuR2ZAVALKw8sfeCgC41ILL3goAjMjv8J0SAKLPhLfdCgCYqYq9/Q8AvKbA6JMKALLk9/fvFgC21sekrwgAyN7Lsa8IALjnjojqDgCAx7GDnhIA7Mea7KILAOaF9KvEEAAAAA=="
    encoded = "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"
    val bytes = Base64.getDecoder.decode(encoded)
    println("production")
    // println(bytesToHex(bytes))
    val srp = srpDecoder.decode(bytes)
    println("decoded srp from saas: " + srp)

    val result = srp.result

    val sb = new StringBuilder
    for (content <- result.content) {
      sb.append(content.rType.toString())
        .append(" pos=")
        .append(content.pos)
        .append(" nResults=")
        .append(content.nResults)
        .append(" nUniqueResults=")
        .append(content.nUniqueResults)
        .append(" isAuto=")
        .append(content.isAuto)
        .append(" isRecallPromotedToPrimary=")
        .append(content.isRecallPromotedToPrimary)
        .append(" listings=")
        ;

      content.listings.foreach {
        listing => sb.append(listing.itemId + " " + listing.variationId + ",")
      }
      sb.append(";")
    }

    println(sb.toString())
  }

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


object SrpGistException {

  def main(args: Array[String]): Unit = {
    val sge = new SrpGistException()
    sge.test2()

  }

}
