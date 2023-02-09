package com.ebay.search.qu

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class ParseSrpRelevanceUDFSuite extends FunSuite {

  test("basic tests") {
    val boostStats = "ItemID:334703177466|ParentID:0|relevanceScore_PEGFB_3_1:3.539140|relevanceScore_RelOrNot_9_1:0.902836|boosttype:relevance|NL:main,ItemID:266090860839|ParentID:0|relevanceScore_PEGFB_3_1:3.489703|relevanceScore_RelOrNot_9_1:0.899113|boosttype:relevance|NL:main,ItemID:465261020228|ParentID:165751310358|relevanceScore_PEGFB_3_1:3.530238|relevanceScore_RelOrNot_9_1:0.889004|boosttype:relevance|NL:keyword_constraint,ItemID:362146698772|ParentID:0|relevanceScore_PEGFB_3_1:3.347452|relevanceScore_RelOrNot_9_1:0.845309|boosttype:relevance|NL:keyword_constraint"
    val parseSrpRelevance = new ParseSrpRelevanceUDF()
    parseSrpRelevance.evaluate("relevanceScore_PEGFB_3_1", "main", 5, boostStats) should be (3.5144215)
    parseSrpRelevance.evaluate("relevanceScore_PEGFB_3_1", "main", 1, boostStats) should be (3.539140)
    parseSrpRelevance.evaluate("relevanceScore_PEGFB_3_1", "keyword_constraint", 10, boostStats) should be (3.438845)
    parseSrpRelevance.evaluate("relevanceScore_PEGFB_3_1", "keyword_constraint", 2, boostStats) should be (3.438845)
    parseSrpRelevance.evaluate("relevanceScore_PEGFB_3_1", "all", 10, boostStats) should be (3.4766332500000003)
    parseSrpRelevance.evaluate("relevanceScore_RelOrNot_9_1", "all", 10, boostStats) should be (0.8840655)
  }

  test("invalid boost_stats input argument should return null") {
    val boostStats = "ItemID:465261020228|ParentID:165751310358|relevanceScore_PEGFB_3_1:3.530238|relevanceScore_RelOrNot_9_1:0.889004|boosttype:invalidrelevance|NL:keyword_constraint,ItemID:362146698772|ParentID:0|relevanceScore_PEGFB_3_1:3.347452|relevanceScore_RelOrNot_9_1:0.845309|boosttype:relevance|NL:invalid_value"
    val parseSrpRelevance = new ParseSrpRelevanceUDF()
    parseSrpRelevance.evaluate("relevanceScore_PEGFB_3_1", "main", 10, boostStats) should be(null)
    parseSrpRelevance.evaluate("relevanceScore_RelOrNot_9_1", "keyword_constraint", 10, boostStats) should be(null)
  }

  test("null boost_stats input argument should return null") {
    val boostStats = null
    val parseSrpRelevance = new ParseSrpRelevanceUDF()
    parseSrpRelevance.evaluate("relevanceScore_PEGFB_3_1", "main", 10, boostStats) should be(null)
    parseSrpRelevance.evaluate("relevanceScore_RelOrNot_9_1", "all", 10, boostStats) should be(null)
  }

  test("invalid module argument should return null") {
    val boostStats = "ItemID:334703177466|ParentID:0|relevanceScore_PEGFB_3_1:3.539140|relevanceScore_RelOrNot_9_1:0.902836|boosttype:relevance|NL:main,ItemID:266090860839|ParentID:0|relevanceScore_PEGFB_3_1:3.489703|relevanceScore_RelOrNot_9_1:0.899113|boosttype:relevance|NL:main,ItemID:465261020228|ParentID:165751310358|relevanceScore_PEGFB_3_1:3.530238|relevanceScore_RelOrNot_9_1:0.889004|boosttype:relevance|NL:keyword_constraint,ItemID:362146698772|ParentID:0|relevanceScore_PEGFB_3_1:3.347452|relevanceScore_RelOrNot_9_1:0.845309|boosttype:relevance|NL:keyword_constraint"
    val parseSrpRelevance = new ParseSrpRelevanceUDF()
    parseSrpRelevance.evaluate("relevanceScore_PEGFB_3_1", "invalidModule", 10, boostStats) should be(null)
  }
}
