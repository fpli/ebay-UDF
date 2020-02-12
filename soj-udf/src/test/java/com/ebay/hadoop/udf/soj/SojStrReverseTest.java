package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SojStrReverseTest {
    @Test
    public void test() {
        assertNull(new SojStrReverse().evaluate(""));
        assertNull(new SojStrReverse().evaluate(null));
        assertEquals("jos", new SojStrReverse().evaluate("soj"));

       }
}