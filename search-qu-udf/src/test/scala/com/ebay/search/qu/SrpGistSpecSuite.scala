package com.ebay.search.qu

import org.scalatest.FunSuite

import java.util.Base64

class SrpGistSpecSuite extends FunSuite {
  test("basic test") {
    val encoded = "wwGnJNADX5smHgAAAgAAEDEyMzQ1Njc4EE9yaWdpbmFslKUE8JgEAAAKdJqY1qXEEADC/rvYtQ8AmNywvPwIALafsLz8CACum6fx1AYAtr/Ku/0PAIaG1fjvFgDOvOG8/Q8AkPXfrcQQAPqSq8D9DwCcktn83QoA5Ij4ockJAKj3vNu1DwD+vcXVjBEA7rey2Z0HAL6p5+TMDQDOwefkzA0ApJffpcQQAIqa8qjWEQDkofX7nRIAvLmqhZQKANjx7/qTCgDqxOPB8BIAgMmW28MQAMKIh4PqDgDEjNLOyAkA3o7ds54HAKrdzLaGDQDSpfzUtQ8Aosqjw/ASAIC504jqDgCGwp+XyQkA8sHqs4YNAN6Mj6vEEACMr9LM3goA0Ovw4ccUAJy6uPzvFgDGzJX+7xYA3PO94dQGAIKej/CCDQDUrKHJzQ0AiPjMgZ4SAK7aw9mTCgDQspirrwgAxsuR2ZAVALKw8sfeCgC41ILL3goAjMjv8J0SAKLPhLfdCgCYqYq9/Q8AvKbA6JMKALLk9/fvFgC21sekrwgAyN7Lsa8IALjnjojqDgCAx7GDnhIA7Mea7KILAOaF9KvEEAAAAA=="
    val bytes = Base64.getDecoder.decode(encoded)
    val udf = new SrpGistSpec
    println(udf.bytesToHex(bytes))
    val srp = udf.srpDecoder.decode(bytes)
    println("decoded srp from saas: " + srp)
  }
}
