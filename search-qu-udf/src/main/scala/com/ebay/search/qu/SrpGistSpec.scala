package com.ebay.search.qu

import com.ebay.tracking._
import org.apache.hadoop.hive.ql.exec.UDF

import java.util.Base64

/* This class is adapted from https://github.corp.ebay.com/search-science/searchtracking/blob/master/src/test/scala/search/tracking/SrpGistSpec.scala
 */ 
class SrpGistSpec extends UDF {
  // lazy val srpDecoder =
  //   new BinaryMessageDecoder[Srp](new SpecificData(), Srp.SCHEMA$)
  lazy val srpDecoder = Decoders.srpGist

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
