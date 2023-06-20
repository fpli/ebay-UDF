package com.ebay.search.qu

import com.ebay.cassini.tokenizer.api.TokenizerArena
import org.apache.hadoop.hive.ql.exec.UDF

import scala.collection.JavaConverters._

class CassiniTokenizerUDF extends UDF {
  /**  Tokenizer used in Cassini. */
  lazy val normalizer = new TokenizerArena(getClass.getClassLoader.getResourceAsStream("tokenizer.json"))
  val sEmpty = new java.lang.String("")

  def evaluate(s:java.lang.String, v:java.lang.String, siteId:java.lang.Long):java.lang.String =
  {
    try {
      // SojHelpers.stringNormalizedDSBEStyle(Option(s), siteId).get
      val s1 = Option(s)
      s1.map(q => normalizer.tokenize(siteId.toString, v, q)
        .getOutputTokenList.asScala.map { t => t.getData }
        .mkString(" "))
        .get
    }
    catch {
      case e: Exception => {
        sEmpty
      }
    }
  }
}
