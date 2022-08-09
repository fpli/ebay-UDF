package com.ebay.hadoop.udf.soj;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SojTagFetcherTest {
    @Test
    public void test() throws Exception {

        assertEquals("3", new SojTagFetcher().evaluate("&a=1&&&b=2&c=3","c"));
        //known bug in original code
        assertEquals("1", new SojTagFetcher().evaluate("&a=1&&&b=2&c=3","a"));
        assertEquals("2", new SojTagFetcher().evaluate("a=1&&&b=2&c=3","b"));
    }
}