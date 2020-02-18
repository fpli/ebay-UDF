package com.ebay.carmel.udf.syslib;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class OtranslateTest implements TestBase {

    @Test
    public void testUdf() throws Exception {
        Otranslate otranslate = new Otranslate();
        assertEquals("9XXX999", otranslate.evaluate("2KRW229",
                "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ", "9999999999XXXXXXXXXXXXXXXXXXXXXXXXXX"));

        assertEquals("XXX", otranslate.evaluate("2KRW229",
                "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ", "99XXX"));
    }
}
