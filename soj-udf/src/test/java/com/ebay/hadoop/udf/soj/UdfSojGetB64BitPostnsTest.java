package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UdfSojGetB64BitPostnsTest {
    @Test
    public void test() {
        assertEquals("5,",new UdfSojGetB64BitPostns().evaluate("BA**",0));
        assertEquals("5,7,8,10,12,13,",new UdfSojGetB64BitPostns().evaluate("Baw*",0));
        assertEquals("",new UdfSojGetB64BitPostns().evaluate("!!**",0));
        assertEquals("5,",new UdfSojGetB64BitPostns().evaluate("BA**",0));
        assertEquals("-5,-7,-8,-10,-12,-13,",new UdfSojGetB64BitPostns().evaluate("Baw*",1));
        assertEquals("",new UdfSojGetB64BitPostns().evaluate("!!**",0));

        assertEquals("3,",new UdfSojGetB64BitPostns().evaluate("EA**",0));
    }
}