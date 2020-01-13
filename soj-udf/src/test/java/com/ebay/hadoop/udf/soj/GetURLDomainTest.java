package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetURLDomainTest {
    @Test
    public void evaluate() throws Exception {
        assertEquals("test.com?param", new GetURLDomain().evaluate("http://test.com?param"));
        assertEquals("shop.ebay.com", new GetURLDomain().evaluate("http://shop.ebay.com/xyz/abc?_nkw=ebay+uk&l1=2&sacat=456"));
        assertEquals(null, new GetURLDomain().evaluate("http:/"));
        assertEquals("shop.ebay.com", new GetURLDomain().evaluate("http://shop.ebay.com/"));
        assertEquals("shop.ebay.com", new GetURLDomain().evaluate("http://shop.ebay.com"));
        assertEquals("shop.ebay.com", new GetURLDomain().evaluate("http://user:password@shop.ebay.com/"));
        assertEquals("test.com", new GetURLDomain().evaluate("http://test.com/a@fda/123"));
        assertEquals(null, new GetURLDomain().evaluate("shop.ebay.com"));
        assertEquals("wwww.ebay.com", new GetURLDomain().evaluate("http://wwww.ebay.com."));
        assertEquals("wwww.ebay.com", new GetURLDomain().evaluate("http://wwww.ebay.com:80"));
        assertEquals("wwww.ebay.com", new GetURLDomain().evaluate("http://user:password@wwww.ebay.com:80"));
        assertEquals("wwww.ebay.com", new GetURLDomain().evaluate("http://user:password@wwww.ebay.com:80&"));
        assertEquals("shop.ebay.com", new GetURLDomain().evaluate("http://shop.ebay.com&"));
        assertEquals("shop.ebay.com", new GetURLDomain().evaluate("http://shop.ebay.com)&"));

        assertEquals(null, new GetURLDomain().evaluate("http:/test.com/a@fda/123"));
        assertEquals(null, new GetURLDomain().evaluate(""));
        assertEquals(null, new GetURLDomain().evaluate(null));
        assertEquals(null, new GetURLDomain().evaluate(" "));
    }
}