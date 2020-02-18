package com.ebay.carmel.udf.syslib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Oreplace2Test implements TestBase {

    @Test
    public void testUdf() throws Exception {
        Oreplace2 oreplace2 = new Oreplace2();
        assertEquals("", oreplace2.evaluate("","",""));
        assertEquals("BLACK and BLUE", oreplace2.evaluate("JACK and JUE","J","BL"));
        assertEquals("abe", oreplace2.evaluate("abcde","cd",""));
        assertEquals("abe", oreplace2.evaluate("abcde","cd",null));
        assertEquals("abcde", oreplace2.evaluate("abcde",null,"xx"));
        assertEquals("abcde", oreplace2.evaluate("abcde","","xx"));
        assertEquals("", oreplace2.evaluate("","abd","xx"));
        assertEquals(null, oreplace2.evaluate(null,"abd","xx"));
    }
}
