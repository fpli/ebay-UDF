package com.ebay.carmel.udf.syslib;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonCheckTest {
  @Test
  public void evaluate() throws Exception {
    String good = "{\"name\" : \"Cameron\", \"age\" : 24, \"schools\" : [ {\"name\" : \"Lake\", \"type\" : \"elementary\"}, {\"name\" : \"Madison\", \"type\" : \"middle\"}, {\"name\" : \"Rancho\", \"type\" : \"high\"}, {\"name\" : \"UCI\", \"type\" : \"college\"} ], \"job\" : \"programmer\" }";
    String bad = "{\"name\" : \"Cameron\", \"age\" : 24, \"schools\" : [ {\"name\" ";
    assertEquals("OK", new JsonCheck().evaluate(new Text(good)).toString());
    assertEquals("INVALID", new JsonCheck().evaluate(new Text(bad)).toString());
    assertEquals(null, new JsonCheck().evaluate(null));
    assertEquals("OK", new JsonCheck().evaluate(new Text(" ")).toString());
    assertEquals("INVALID", new JsonCheck().evaluate(new Text("42")).toString());
    assertEquals("INVALID", new JsonCheck().evaluate(new Text("true")).toString());
    assertEquals("INVALID", new JsonCheck().evaluate(new Text("false")).toString());
  }
}
