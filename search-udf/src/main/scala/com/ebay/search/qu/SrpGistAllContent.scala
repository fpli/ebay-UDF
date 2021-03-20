
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
 * A UDF to get serialized content of a SRP extracted from a SRP tracking input.
 */ 
class SrpGistAllContent extends UDF {
  // lazy val srpDecoder =
  //   new BinaryMessageDecoder[Srp](new AllContentificData(), Srp.SCHEMA$)
  lazy val srpDecoder = Decoders.srpGist

  val sEmpty = new java.lang.String("")

  /**
   * This method checks if a SRP is low.
   *
   * @return 1 if the input SRP is low, 0 for all other situations including exception
   */
  def evaluate(s:java.lang.String, base64_flag:java.lang.Integer, url_decode_flag:java.lang.Integer):java.lang.String =
  {
    var s1:java.lang.String = s

    try {
      if (url_decode_flag == 1) {
        s1 = URLDecoder.decode(s, "UTF-8")
      }

      var srp: Srp= null
      if (base64_flag ==  1)  {
        val bytes = Base64.getDecoder.decode(s1)
        srp = srpDecoder.decode(bytes)
      }
      else {
        srp = srpDecoder.decode(s1.getBytes())
      }

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

      sb.toString()
    }
    catch {
      case e: Exception => {
        return sEmpty
      }
    }
  }
}
