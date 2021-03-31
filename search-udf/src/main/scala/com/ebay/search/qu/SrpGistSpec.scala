package com.ebay.search.qu

import java.util.Base64

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

/* This class is adapted from https://github.corp.ebay.com/search-science/searchtracking/blob/master/src/test/scala/search/tracking/SrpGistSpec.scala
 */ 
class SrpGistSpec extends UDF {
  // lazy val srpDecoder =
  //   new BinaryMessageDecoder[Srp](new SpecificData(), Srp.SCHEMA$)
  lazy val srpDecoder = Decoders.srpGist

  def test2() {
    val encoded = "wwGnJNADX5smHgAAAgAAEDEyMzQ1Njc4EE9yaWdpbmFslKUE8JgEAAAKdJqY1qXEEADC/rvYtQ8AmNywvPwIALafsLz8CACum6fx1AYAtr/Ku/0PAIaG1fjvFgDOvOG8/Q8AkPXfrcQQAPqSq8D9DwCcktn83QoA5Ij4ockJAKj3vNu1DwD+vcXVjBEA7rey2Z0HAL6p5+TMDQDOwefkzA0ApJffpcQQAIqa8qjWEQDkofX7nRIAvLmqhZQKANjx7/qTCgDqxOPB8BIAgMmW28MQAMKIh4PqDgDEjNLOyAkA3o7ds54HAKrdzLaGDQDSpfzUtQ8Aosqjw/ASAIC504jqDgCGwp+XyQkA8sHqs4YNAN6Mj6vEEACMr9LM3goA0Ovw4ccUAJy6uPzvFgDGzJX+7xYA3PO94dQGAIKej/CCDQDUrKHJzQ0AiPjMgZ4SAK7aw9mTCgDQspirrwgAxsuR2ZAVALKw8sfeCgC41ILL3goAjMjv8J0SAKLPhLfdCgCYqYq9/Q8AvKbA6JMKALLk9/fvFgC21sekrwgAyN7Lsa8IALjnjojqDgCAx7GDnhIA7Mea7KILAOaF9KvEEAAAAA=="
    val bytes = Base64.getDecoder.decode(encoded)
    println("production")
    println(bytesToHex(bytes))
    val srp = srpDecoder.decode(bytes)
    println("decoded srp from saas: " + srp)
  }

  // def bytesToHex(bytes: Array[Byte]): String = bytes.map(String.format("%02X", java.lang.Byte.valueOf(_))).mkString(" ")

  def bytesToHex(bytes : Array[Byte]) = bytes.map{
    b => String.format("%02X", new java.lang.Integer(b & 0xff))
  }.mkString


  val sEmpty = new java.lang.String("")
  val sa0=new Array[java.lang.String](0)

  def evaluate(s:java.lang.String):java.lang.Integer =
  {
    try {
      val bytes = Base64.getDecoder.decode(s)
      val srp = srpDecoder.decode(bytes)
      val result = srp.result
      if (result.isNull) {
        return 1
      }

      return 0
    }
    catch {
      case e: Exception => {
        return -1
      }
    }
  }
}
