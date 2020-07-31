package com.ebay.carmel.udf.datelib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Epoch2TimestampTest {
    @Test
    public void test() {
        Epoch2Timestamp epoch2Timestamp = new Epoch2Timestamp();
        assertEquals("2020-03-17 18:02:08.000", epoch2Timestamp.evaluate(1584439328l).toString());
        assertEquals("2010-01-01 16:01:01.000", epoch2Timestamp.evaluate(1262332861l).toString());
    }
}
