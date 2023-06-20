package com.ebay.search.qu

import org.scalatest.FunSuite

class VnfNormalizerUDFSuite extends FunSuite {
  test("basic test") {
    val vnfNormalizer = new VnfNormalizerUDF()
    val eval = vnfNormalizer.evaluate("colour","red 1/ 2",0,0)
    assert(eval == "red 1/2")
  }
}
