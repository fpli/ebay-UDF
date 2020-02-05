package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CstrStrchrTest {
    @Test
    public void test() {
        assertEquals(".ebay.com", new CstrStrchr().evaluate("www.ebay.com", "."));
        assertEquals("ipod", new CstrStrchr().evaluate("apple ipod", "i"));
        assertNull(new CstrStrchr().evaluate("apple ipod", "z"));
        assertNull(new CstrStrchr().evaluate("apple ipod", ""));
    }
}