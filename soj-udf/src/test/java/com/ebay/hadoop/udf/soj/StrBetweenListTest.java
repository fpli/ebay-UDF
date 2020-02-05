package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StrBetweenListTest {
    @Test
    public void test() {
        assertNull(new StrBetweenList().evaluate("Opera/9.80 ()", "(", ")"));
        assertEquals("Windows NT 5.1, U, en Presto/2.2.15 Version/10.10", new StrBetweenList().evaluate("Opera/9.80 (Windows NT 5.1, U, en Presto/2.2.15 Version/10.10", "(", ")"));
        assertNull(new StrBetweenList().evaluate("Opera/9.80 (", "(", ")"));
        assertNull(new StrBetweenList().evaluate("Opera/9.80 Windows NT 5.1, U, en) Presto/2.2.15 Version/10.10", "(", ")"));
        assertEquals("Opera/9.80 Windows NT 5.1", new StrBetweenList().evaluate("Opera/9.80 Windows NT 5.1, U, en) Presto/2.2.15 Version/10.10", null, "),"));
        assertEquals("Opera/9.80 Windows NT 5.1", new StrBetweenList().evaluate("Opera/9.80 Windows NT 5.1) Presto/2.2.15, Version/10.10", null, "),"));
    }
}