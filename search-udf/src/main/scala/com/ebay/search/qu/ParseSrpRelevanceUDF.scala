package com.ebay.search.qu

import com.ebay.hadoop.scalaplatform.helpers.SojHelpers
import org.apache.hadoop.hive.ql.exec.UDF

class ParseSrpRelevanceUDF extends UDF {

  def evaluate(relevanceModel: java.lang.String, module: java.lang.String, topN: java.lang.Integer, boostStats: java.lang.String): java.lang.Double = {
    /** relevanceModel should be either "relevanceScore_PEGFB_3_1" or "relevanceScore_RelOrNot_9_1"
     * module should be "main", "keyword_constraint" or other recovery types; or use "all" to include all types
     * topN should be an integer indicating the top n items on the srp over which you want to calculate the mean relevance
     * boostStats is a comma (,) separate list of, pipe (|) separated list of, colon (:) separated pair of key values.
     */
    boostStats match {
      case null => null
      case boostStats => {
        val boostStatsDecoded = SojHelpers.urlDecodeMultipleTimes(boostStats)
        val KeyValueRegEx = """(\S*):(\S*)""".r
        var relevanceSeqMap = boostStatsDecoded
          .split(",")
          .toSeq
          .map(
            _.split("""\|""")
              .flatMap({
                case KeyValueRegEx(key, value) => Some(key -> value)
                case _ => None
              })
              .toMap
          ) // Seq[Map[String, String]]
          .filter(x => x.get("boosttype").exists(_.equals("relevance")))

        relevanceSeqMap = if (module == "all") relevanceSeqMap else relevanceSeqMap.filter(x => x.get("NL").exists(_.equals(module)))

        relevanceSeqMap = relevanceSeqMap.take(topN)

        relevanceSeqMap.length match {
          case 0 => null
          case _ => relevanceSeqMap.map(x => SojHelpers.safeToDouble(x.get(relevanceModel))).flatten.sum / relevanceSeqMap.length
        }
      }
    }
  }
}
