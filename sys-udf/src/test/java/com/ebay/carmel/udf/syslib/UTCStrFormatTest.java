package com.ebay.carmel.udf.syslib;

import org.junit.Test;

import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

public class UTCStrFormatTest implements TestBase
{
    @Test
    public void testUdf() throws Exception {
      TimeZone defaultZone = TimeZone.getDefault();
      TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
      try {
            UTCStrFormat utcStrFormat = new UTCStrFormat();
            assertEquals("1970-01-01 00:00:10", utcStrFormat.evaluate(10));
            assertEquals("1970-01-01 00:00:06", utcStrFormat.evaluate(6));
        } finally {
            TimeZone.setDefault(defaultZone);
        }
    }
}
