package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetURLPathTest {
    @Test
    public void evaluate() throws Exception {
        assertEquals(null, new GetURLPath().evaluate("http://test.com?param"));
        assertEquals("/xyz/abc", new GetURLPath().evaluate("http://shop.ebay.com/xyz/abc?_nkw=ebay+uk&l1=2&sacat=456"));
        assertEquals("/", new GetURLPath().evaluate("http://shop.ebay.com/"));
        assertEquals("/", new GetURLPath().evaluate("http://shop.ebay.com/?"));
        assertEquals(null, new GetURLPath().evaluate("http://shop.ebay.com?"));

        assertEquals(null, new GetUrlParams().evaluate("http:/"));
        assertEquals(null, new GetURLPath().evaluate(""));
        assertEquals(null, new GetURLPath().evaluate(null));
        assertEquals(null, new GetURLPath().evaluate(" "));
    }
}