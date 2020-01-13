package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StrBetweenEndListTest {
    @Test
    public void test() {
        assertEquals("11021", new StrBetweenEndList().evaluate("e11021.m43.l3160","e",".mle"));
        assertNull(new StrBetweenEndList().evaluate("e11021.m43.l3160","",".mle"));
        assertEquals("e11021.m43.l3160", new StrBetweenEndList().evaluate("e11021.m43.l3160","",""));
        assertEquals("11021.m43.l3160", new StrBetweenEndList().evaluate("e11021.m43.l3160","e",""));
        assertNull(new StrBetweenEndList().evaluate("e11021.m43.l3160",null,".mle"));
        assertEquals("e11021.m43.l3160", new StrBetweenEndList().evaluate("e11021.m43.l3160",null,null));
        assertEquals("11021.m43.l3160", new StrBetweenEndList().evaluate("e11021.m43.l3160","e",null));
        assertNull(new StrBetweenEndList().evaluate(null,null,null));
        assertEquals("e11021", new StrBetweenEndList().evaluate("e11021.m43.l3160",null,".m"));
        assertEquals("3160", new StrBetweenEndList().evaluate("e11021.m43.l3160","l",".eml"));
        assertEquals(null, new StrBetweenEndList().evaluate("abc","c","c"));
        assertEquals(null, new StrBetweenEndList().evaluate("abc","b","c"));
        assertEquals("1", new StrBetweenEndList().evaluate("e13001.m1.l1","l",".eml"));
        assertEquals(null, new StrBetweenEndList().evaluate("e13001.m1.l","l",".eml"));
        assertEquals(null, new StrBetweenEndList().evaluate("e13001.m1.l","","e"));
        assertEquals("13001.m1.l", new StrBetweenEndList().evaluate("e13001.m1.l","e","e"));
    }
}