package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SojUrlExtractNvpTest {
    @Test
    public void test() throws Exception {
        assertEquals("2", new SojUrlExtractNvp().evaluate("QQaZ1QQbZ2QQcZ3","b",1));
        assertEquals("3", new SojUrlExtractNvp().evaluate("&a=1&b=2&c=3","c",1));
        assertEquals("2", new SojUrlExtractNvp().evaluate("aZ1QQbZ2QQcZ3","b",1));
        assertEquals("2", new SojUrlExtractNvp().evaluate("aZ1QQbZ2QQcZ3","_b",2));

        //known bug in original code
        assertEquals(null, new SojUrlExtractNvp().evaluate("a=1&b=2&c=3","a",1));
        assertEquals(null, new SojUrlExtractNvp().evaluate(
            "/sch/Auto-Ersatz-Reparaturteile/9884/i.html?MModel=&_fosrp=&MMake=&_mcatda=&mvsub=",
            "_nkw", 0));
    }
}