package com.ebay.carmel.udf.syslib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UTF8ToUTF16Test implements TestBase {

    @Test
    public void testUdf() throws Exception {
        UTF8ToUTF16 utf8ToUTF16 = new UTF8ToUTF16();
        assertEquals("burhandalyanlar@hotmail.com", utf8ToUTF16.evaluate("burhandalyanlar@hotmail.com"));
        assertEquals(null, utf8ToUTF16.evaluate(""));
        assertEquals(null, utf8ToUTF16.evaluate(null));
    }
}
