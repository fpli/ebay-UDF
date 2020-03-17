package com.ebay.carmel.udf.datelib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Epoch2dateTest {
    @Test
    public void test() {
        Epoch2Date epoch2date = new Epoch2Date();
        assertEquals("2010-01-01", epoch2date.evaluate(1262332862).toString());
    }
}
