package com.ebay.carmel.udf.datelib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Epochms2TimestampTest extends DateTestBase {
    @Test
    public void test() throws Exception {
        withUtcTimeZone(() -> {
            Epochms2Timestamp epochms2Timestamp = new Epochms2Timestamp();
            assertEquals("2020-03-17 10:02:08.000", epochms2Timestamp.evaluate(1584439328000l).toString());
            return null;
        });
    }
}
