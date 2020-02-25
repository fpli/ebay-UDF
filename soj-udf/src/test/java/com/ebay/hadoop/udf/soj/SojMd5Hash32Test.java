package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SojMd5Hash32Test {
    @Test
    public void evaluate() throws Exception {
        assertEquals(Integer.valueOf(2), new SojMd5Hash32().evaluate("0000007e12a0a0e20196e7f6fe84b8e5", (String)null, (String)null, 100));
        assertEquals(Integer.valueOf(47), new SojMd5Hash32().evaluate("0000007e12a0a0e20196e7f6fe84b8e5", "13", (String)null, 100));
        assertEquals(Integer.valueOf(72), new SojMd5Hash32().evaluate("0000007e12a0a0e20196e7f6fe84b8e5", "EXPT", (String)null, 100));
        assertEquals(Integer.valueOf(90), new SojMd5Hash32().evaluate("0000007e12a0a0e20196e7f6fe84b8e5", (String)null, "4000", 100));
        assertEquals(Integer.valueOf(26), new SojMd5Hash32().evaluate("0000007e12a0a0e20196e7f6fe84b8e5", "13", "4000", 100));
    }
}