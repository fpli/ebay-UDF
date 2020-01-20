package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StrBetweenStrTest {
    @Test
    public void test() {
        assertNull(new StrBetweenEndList().evaluate("Opera/9.80 ()", "(", ")"));
        assertEquals("Windows NT 5.1; U; en", new StrBetweenEndList().evaluate("Opera/9.80 (Windows NT 5.1; U; en) Presto/2.2.15 Version/10.10", "(", ")"));
        assertEquals("Windows NT 5.1; U; en Presto/2.2.15 Version/10.10", new StrBetweenEndList().evaluate("Opera/9.80 (Windows NT 5.1; U; en Presto/2.2.15 Version/10.10", "(", ")"));
        assertNull(new StrBetweenEndList().evaluate("Opera/9.80 (", "(", ")"));
        assertNull(new StrBetweenEndList().evaluate("Opera/9.80 Windows NT 5.1; U; en) Presto/2.2.15 Version/10.10", "(", ")"));
        assertNull(new StrBetweenEndList().evaluate("Opera/9.80 (Windows NT 5.1; U; en)", ")", "("));
        assertEquals("Opera/9.80 (Windows NT 5.1; U; en)", new StrBetweenEndList().evaluate("Opera/9.80 (Windows NT 5.1; U; en)", null, null));
        assertEquals("Opera/9.80 (Windows NT 5.1", new StrBetweenEndList().evaluate("Opera/9.80 (Windows NT 5.1; U; en)", null, ";"));
        assertEquals("Opera/9.80 (Windows NT 5.1; U; en)", new StrBetweenEndList().evaluate("Opera/9.80 (Windows NT 5.1; U; en)", null, "Z"));
        assertEquals("lla'", new StrBetweenEndList().evaluate("Mozilla'is somebody''s best fiend", "i", "i"));
    }
}