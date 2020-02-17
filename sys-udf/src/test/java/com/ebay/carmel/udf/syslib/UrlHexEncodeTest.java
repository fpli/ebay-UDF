package com.ebay.carmel.udf.syslib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UrlHexEncodeTest implements TestBase {
    @Test
    public void testUdf() throws Exception {
        UrlHexEncode urlHexEncode = new UrlHexEncode();
        assertEquals("http://user:pass@ebay.com:80/view_item?ui=123&g=abc", urlHexEncode.evaluate("http://user:pass@ebay.com:80/view_item?ui=123&g=abc"));
        assertEquals("http://abc.com/%3cabc%3e/%25$%25%5e%5e&%5e&/", urlHexEncode.evaluate("http://abc.com/<abc>/%$%^^&^&/"));
    }
}
