package com.ebay.search.qu

import com.ebay.search.util.TagNameNotFoundException
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.{DeferredJavaObject, DeferredObject}
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector
import org.apache.hadoop.hive.serde2.objectinspector.primitive.{JavaConstantStringObjectInspector, PrimitiveObjectInspectorFactory}
import org.apache.hadoop.hive.serde2.typeinfo.PrimitiveTypeInfo
import org.apache.hadoop.io.Text
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class ComplexTagUDFSuit extends FunSuite {
  val payload = "wwGnJNADX5smHgAAAgAAEDEyMzQ1Njc4EE9yaWdpbmFs4JoFxusBAAAKZp6HneW2Ce7BtJjqGp" +
    "SxkbbpEpj1zsnvHeCO7LfpEpCHgYfqHYLKyd6yF+iepqONJ9L6p7eGB4Sy9rC6GNCMjabAEOzP0PKDIoaxpYue" +
    "Eua30ebQI9aF5MWqE+bBgfS/H66f06rCDYTl6Nn3HoLyq/y8BsDEtuHwF8aQyprDEPSm0ZGJIqqbseS8BqLKgd" +
    "DwF87SlLiGB+Cyi6u6GPSx7pHEELzf5LSKIozu28TWEdygtoWMI7rS3PO/EO716PGCIqjXr9qxF6L6xICMJ+T0" +
    "55GeB+bhzfnfGKap+LjBEOaR39eGIpziyrS/EMSQoe+BItCVsIy9Btj16enwF8D4x+W8BsSC9tDwF6KOv7TEEL" +
    "jwytuKIuzLvL+IB/KjsOG7GPLchJzNFZrtjdeiJZSewdC8Bvzcvd7wF9jsu6W0F7ien8GPJ5iE/M61F66Q/ZqR" +
    "J+7+otXAEMTfjPGEIpyz3be8Bry4zbTwF/br38WqE/TO/PO/H9aSld7AEK7WsIeFIqjCkveHB7bSr6W7GLLFkdL" +
    "DEMre3saJIpCL4fHMFaKBo6iiJdqin9q8Bq638cfwF/7risjAEObB7NGEIuS8hqPEEMztlcmKIpjxxafjBwD228" +
    "K23wuW6Mfymh2QpN3pnQe8hfK03xj4pJiGmwf8gMXl2xi+rdyxvxDwyY3pgSKyvaW+8BLwz72S8R3w3YivwAaor" +
    "ceL9Bf28ZWjwBC0xqbpgyLewda5ngf8hqWc4BjqnO63wRDQ57jVhiL6lrPewRDckPmchyLijuPMhgegk5m0uhjC" +
    "yMu3jgr68M/avxsAAA=="

  test("Invalid Tag Name should throw TagNameNotFoundException") {
    val tagName = "srpgist"
    val stringOITagName = new JavaConstantStringObjectInspector(tagName)
    val stringOIPayload = new JavaConstantStringObjectInspector(payload)
    val objectInspectorArray : Array[ObjectInspector] = Array(stringOIPayload, stringOITagName)

    intercept[TagNameNotFoundException] {
      new ComplexTagUDF().initialize(objectInspectorArray)
    }
  }

  test("Null Payload should return null") {
    val stringOITagName = new JavaConstantStringObjectInspector("srpGist")
    val deferredObjectArray: Array[DeferredObject] = Array(new DeferredJavaObject(null),
      new DeferredJavaObject(stringOITagName))
   new ComplexTagUDF().evaluate(deferredObjectArray) should be (null)
  }

  test("Init should verify parameters") {
    val stringOIPayload = PrimitiveObjectInspectorFactory.javaStringObjectInspector
    val tagStringType  = new PrimitiveTypeInfo()
    tagStringType.setTypeName("string")
    val stringOITagName = PrimitiveObjectInspectorFactory.getPrimitiveWritableConstantObjectInspector(tagStringType,
      new Text("srpGist"))
    val udfObject = new ComplexTagUDF()
    val objectInspectorArray : Array[ObjectInspector] = Array(stringOIPayload, stringOITagName)
    udfObject.initialize(objectInspectorArray)
  }


  test("Good Payload should return non-null value") {
    val stringOIPayload = PrimitiveObjectInspectorFactory.javaStringObjectInspector
    val stringOITagName  = new JavaConstantStringObjectInspector("srpGist")
    val udfObject = new ComplexTagUDF()
    val objectInspectorArray : Array[ObjectInspector] = Array(stringOIPayload, stringOITagName)
    udfObject.initialize(objectInspectorArray)
    val deferredObjectArray: Array[DeferredObject] = Array(new DeferredJavaObject(payload),
      new DeferredJavaObject("srpGist"))
    udfObject.evaluate(deferredObjectArray) shouldNot be (null)
    println(udfObject.evaluate(deferredObjectArray))
  }
}
