package com.ebay.carmel.udf.syslib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UTCStrFormatTest implements TestBase
{
    @Test
    public void testUdf() throws Exception {
        UTCStrFormat utcStrFormat = new UTCStrFormat();
        assertEquals("1970-01-01 08:00:10", utcStrFormat.evaluate(10));
        assertEquals("1970-01-01 08:00:06", utcStrFormat.evaluate(6));
    }
}
