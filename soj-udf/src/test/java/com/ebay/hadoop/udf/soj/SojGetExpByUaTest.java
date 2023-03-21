package com.ebay.hadoop.udf.soj;

import static org.junit.Assert.assertEquals;

import org.apache.hadoop.io.Text;
import org.junit.Test;

public class SojGetExpByUaTest {

  @Test
  public void evaluate() throws Exception {
    assertEquals(new Text("mweb"), new SojGetExpByUa().evaluate("Mozilla/5.0 (iPhone; CPU "
        + "iPhone OS 15_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.5 Mobile/15E148 Safari/604.1"));
    assertEquals(new Text("dweb"), new SojGetExpByUa().evaluate("Mozilla/5.0 (Macintosh; "
        + "Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.3 Safari/605.1.15"));
    assertEquals(new Text("native"), new SojGetExpByUa().evaluate("ebayUserAgent/eBayAndroid"
        + ";6.15.0;Android;7.0;unknown;generic_x86_64;Android;1080x1776;3.0,origUserId"));
  }
}
