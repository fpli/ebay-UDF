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
    parseSrpRelevance.evaluate("relevance_score", "keyword_constraint", 2, boostStats) should be (3.438845)
    parseSrpRelevance.evaluate("relevanceScore_PEGFB_3_1", "all", 10, boostStats) should be (3.4766332500000003)
    parseSrpRelevance.evaluate("relevance_score", "all", 10, boostStats) should be (3.4766332500000003)
    parseSrpRelevance.evaluate("relevanceScore_RelOrNot_9_1", "all", 10, boostStats) should be (0.8840655)
    parseSrpRelevance.evaluate("relevance_score_binary", "all", 10, boostStats) should be (0.8840655)
  }

  test("basic tests after schema changes") {
    val boostStats = "ItemID:334703177466|ParentID:0|relevance_score:3.539140|relevance_score_binary:0.902836|boosttype:relevance|NL:main,ItemID:266090860839|ParentID:0|relevance_score:3.489703|relevance_score_binary:0.899113|boosttype:relevance|NL:main,ItemID:465261020228|ParentID:165751310358|relevance_score:3.530238|relevance_score_binary:0.889004|boosttype:relevance|NL:keyword_constraint,ItemID:362146698772|ParentID:0|relevance_score:3.347452|relevance_score_binary:0.845309|boosttype:relevance|NL:keyword_constraint"
    val parseSrpRelevance = new ParseSrpRelevanceUDF()
    parseSrpRelevance.evaluate("relevance_score", "main", 5, boostStats) should be(3.5144215)
    parseSrpRelevance.evaluate("relevance_score", "main", 1, boostStats) should be(3.539140)
    parseSrpRelevance.evaluate("relevance_score", "keyword_constraint", 10, boostStats) should be(3.438845)
    parseSrpRelevance.evaluate("relevance_score", "keyword_constraint", 2, boostStats) should be(3.438845)
    parseSrpRelevance.evaluate("relevanceScore_PEGFB_3_1", "keyword_constraint", 2, boostStats) should be(3.438845)
    parseSrpRelevance.evaluate("relevance_score", "all", 10, boostStats) should be(3.4766332500000003)
    parseSrpRelevance.evaluate("relevanceScore_PEGFB_3_1", "all", 10, boostStats) should be(3.4766332500000003)
    parseSrpRelevance.evaluate("relevance_score_binary", "all", 10, boostStats) should be(0.8840655)
    parseSrpRelevance.evaluate("relevanceScore_RelOrNot_9_1", "all", 10, boostStats) should be(0.8840655)
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
    parseSrpRelevance.evaluate("relevance_score", "invalidModule", 10, boostStats) should be(null)
  }

  test("tests for optional arguments of excludeCPC, excludeCPA and clktrack") {
    val boostStats = "ItemID:334703177466|ParentID:0|relevance_score:3.539140|relevance_score_binary:0.902836|boosttype:relevance|NL:main,ItemID:266090860839|ParentID:0|relevance_score:3.489703|relevance_score_binary:0.899113|boosttype:relevance|NL:main,ItemID:465261020228|ParentID:165751310358|relevance_score:3.530238|relevance_score_binary:0.889004|boosttype:relevance|NL:main,ItemID:362146698772|ParentID:0|relevance_score:3.347452|relevance_score_binary:0.845309|boosttype:relevance|NL:keyword_constraint,ItemID:123456|ParentID:0|relevance_score:3.839120|relevance_score_binary:0.922136|boosttype:relevance|NL:keyword_constraint,ItemID:7889876|ParentID:0|relevance_score:3.019120|relevance_score_binary:0.862136|boosttype:relevance|NL:keyword_constraint"
    val clktrack = "1024,0,1024,256,0,256"
    val parseSrpRelevance = new ParseSrpRelevanceUDF()
    parseSrpRelevance.evaluate("relevanceScore_PEGFB_3_1", "all", 10, boostStats, excludeCPC=1, excludeCPA=1, clktrack=clktrack) should be(3.6644115)
    parseSrpRelevance.evaluate("relevance_score", "all", 1, boostStats, excludeCPC=1, excludeCPA=1, clktrack=clktrack) should be(3.489703)
    parseSrpRelevance.evaluate("relevance_score", "all", 10, boostStats, 0, excludeCPA=1, clktrack=clktrack) should be(3.59955025)
    parseSrpRelevance.evaluate("relevance_score", "all", 1, boostStats, 0, excludeCPA=1, clktrack=clktrack) should be(3.53914)
    parseSrpRelevance.evaluate("relevance_score", "all", 10, boostStats, excludeCPC=1, 0, clktrack=clktrack) should be(3.4238487500000003)
    parseSrpRelevance.evaluate("relevance_score", "all", 1, boostStats, excludeCPC=1, 0, clktrack=clktrack) should be(3.489703)
    parseSrpRelevance.evaluate("relevance_score_binary", "all", 10, boostStats, excludeCPC=1, 0, clktrack=clktrack) should be(0.8821735)
    parseSrpRelevance.evaluate("relevance_score", "all", 10, boostStats, excludeCPC=1, 0, clktrack=null) should be(3.4607955)
    parseSrpRelevance.evaluate("relevance_score", "all", 10, boostStats, excludeCPC=1, excludeCPA=1, null) should be(3.4607955)
    parseSrpRelevance.evaluate("relevance_score", "all", 10, boostStats, excludeCPC=2, excludeCPA=3, null) should be(3.4607955)
    parseSrpRelevance.evaluate("relevance_score", "all", 10, boostStats, excludeCPC=0, excludeCPA=0, clktrack=clktrack) should be(3.4607955)
  }
}
