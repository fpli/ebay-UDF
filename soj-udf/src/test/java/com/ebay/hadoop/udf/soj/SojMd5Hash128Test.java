package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SojMd5Hash128Test {
    @Test
    public void evaluate() throws Exception {
        assertEquals(Integer.valueOf(37), new SojMd5Hash128().evaluate("0000007e12a0a0e20196e7f6fe84b8e5", (String)null, (String)null, 100));
        assertEquals(Integer.valueOf(91), new SojMd5Hash128().evaluate("0000007e12a0a0e20196e7f6fe84b8e5", "13", (String)null, 100));
        assertEquals(Integer.valueOf(28), new SojMd5Hash128().evaluate("0000007e12a0a0e20196e7f6fe84b8e5", "EXPT", (String)null, 100));
        assertEquals(Integer.valueOf(46), new SojMd5Hash128().evaluate("0000007e12a0a0e20196e7f6fe84b8e5", (String)null, "4000", 100));
        assertEquals(Integer.valueOf(14), new SojMd5Hash128().evaluate("0000007e12a0a0e20196e7f6fe84b8e5", "13", "4000", 100));
        assertEquals(null, new SojMd5Hash128().evaluate("0000007e12a0a0e20196e7f6fe84b8e5", "13", "4000", null));
        assertEquals(null, new SojMd5Hash128().evaluate("0000007e12a0a0e20196e7f6fe84b8e5", "13", "4000", -1));
        assertEquals(null, new SojMd5Hash128().evaluate(null, "13", "4000", 100));
    }
}