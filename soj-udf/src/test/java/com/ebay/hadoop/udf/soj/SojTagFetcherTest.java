package com.ebay.hadoop.udf.soj;

import static org.junit.Assert.assertEquals;

import org.apache.hadoop.io.Text;
import org.junit.Test;

public class SojTagFetcherTest {
    @Test
    public void test() throws Exception {

        assertEquals(new Text("3"), new SojTagFetcher().evaluate(new Text("&a=1&&&b=2&c=3"),"c"));
        //known bug in original code
        assertEquals(new Text("1"), new SojTagFetcher().evaluate(new Text("&a=1&&&b=2&c=3"),"a"));
        assertEquals(new Text("2"), new SojTagFetcher().evaluate(new Text("a=1&&&b=2&c=3"),"b"));
    }
}