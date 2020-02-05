package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StrBetweenTest {
    @Test
    public void test() {
        assertNull(new StrBetween().evaluate("Opera/9.80 ()", '(', ')'));
        assertEquals("Windows NT 5.1; U; en", new StrBetween().evaluate("Opera/9.80 (Windows NT 5.1; U; en) Presto/2.2.15 Version/10.10", '(', ')'));
        assertEquals("Windows NT 5.1; U; en Presto/2.2.15 Version/10.10", new StrBetween().evaluate("Opera/9.80 (Windows NT 5.1; U; en Presto/2.2.15 Version/10.10", '(', ')'));
        assertNull(new StrBetween().evaluate("Opera/9.80 (", '(', ')'));
        assertNull(new StrBetween().evaluate("Opera/9.80 Windows NT 5.1; U; en) Presto/2.2.15 Version/10.10", '(', ')'));
        assertNull(new StrBetween().evaluate("Opera/9.80 (Windows NT 5.1; U; en)", ')', '('));
        assertEquals("lla'", new StrBetween().evaluate("Mozilla'is somebody''s best fiend", 'i', 'i'));
    }
}