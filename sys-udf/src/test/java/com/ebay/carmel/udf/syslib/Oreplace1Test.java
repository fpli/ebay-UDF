package com.ebay.carmel.udf.syslib;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Oreplace1Test implements TestBase {

    @Test
    public void testUdf() throws Exception {
        Oreplace1 oreplace1 = new Oreplace1();
        assertEquals("", oreplace1.evaluate("",""));
        assertEquals("ACK and UE", oreplace1.evaluate("JACK and JUE","J"));
        assertEquals("abe", oreplace1.evaluate("abcde","cd"));
        assertEquals("abcde", oreplace1.evaluate("abcde",null));
    }
}
