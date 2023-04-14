package com.ebay.search.qu

import com.ebay.hadoop.scalaplatform.helpers.SojHelpers
import org.apache.hadoop.hive.ql.exec.UDF

class ParseSrpRelevanceUDF extends UDF {

  def evaluate(relevanceModel: java.lang.String, module: java.lang.String, topN: java.lang.Integer, boostStats: java.lang.String, excludeCPC: java.lang.Integer, excludeCPA: java.lang.Integer, clktrack: java.lang.String): java.lang.Double = {
    /** relevanceModel should be either "relevance_score" or "relevance_score_binary".
     * module should be "main", "keyword_constraint", “category_constraint”, “site_constraint”, "spell_check" or other recovery types; or use "all" to include all types.
     * topN should be an integer indicating the top n items on the srp over which you want to calculate the mean relevance. Set it large enough (like 500) to include all items on SRP.
     * boostStats is a comma (,) separate list of, pipe (|) separated list of, colon (:) separated pair of key values.
     * excludeCPC is whether to exclude CPC in relevance calculation. Default value is 0 (not to exclude). If set to 1, then clktrack shouldn't be null, otherwise excludeCPC won't take effect.
     * excludeCPA is whether to exclude CPA in relevance calculation. Default value is 0 (not to exclude). If set to 1, then clktrack shouldn't be null, otherwise excludeCPA won't take effect.
     * clktrack is a comma seperated list of values indicating whether this item is organic, CPC or CPA. Example: 1024,0,0,256,0.
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

        relevanceSeqMap = clktrack match {
          case null => relevanceSeqMap
          case _ => {
            var tmpSeqMap = relevanceSeqMap.zip(clktrack.split(",").toSeq.map(_.toInt))
            tmpSeqMap = excludeCPC.intValue() match {
              case 1 => tmpSeqMap.filter(x => x._2 != 1024)
              case _ => tmpSeqMap
            }
            tmpSeqMap = excludeCPA.intValue() match {
              case 1 => tmpSeqMap.filter(x => x._2 != 256)
              case _ => tmpSeqMap
            }
            tmpSeqMap.map(x => x._1)
          }
        }

        relevanceSeqMap = if (module == "all") relevanceSeqMap else relevanceSeqMap.filter(x => x.get("NL").exists(_.equals(module)))

        relevanceSeqMap = relevanceSeqMap.take(topN)

        val relevanceModelAlt = relevanceModel match {
          case "relevanceScore_PEGFB_3_1" => "relevance_score"
          case "relevance_score" => "relevanceScore_PEGFB_3_1"
          case "relevanceScore_RelOrNot_9_1" => "relevance_score_binary"
          case "relevance_score_binary" => "relevanceScore_RelOrNot_9_1"
        }

        relevanceSeqMap.length match {
          case 0 => null
          case _ => relevanceSeqMap.map(x => SojHelpers.safeToDouble(x.get(relevanceModel) orElse x.get(relevanceModelAlt))).flatten.sum / relevanceSeqMap.length
        }
      }
    }
  }

  def evaluate(relevanceModel: java.lang.String, module: java.lang.String, topN: java.lang.Integer, boostStats: java.lang.String): java.lang.Double = {
    evaluate(relevanceModel, module, topN, boostStats, 0, 0, null)
  }
}
