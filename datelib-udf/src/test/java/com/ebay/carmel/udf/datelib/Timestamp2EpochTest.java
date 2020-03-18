package com.ebay.carmel.udf.datelib;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class Timestamp2EpochTest {
    @Test
    public void test() throws ParseException {
        Timestamp2Epoch timestamp2Epoch = new Timestamp2Epoch();
        assertEquals("1584468729", timestamp2Epoch.evaluate(new Text("2020-03-17 18:12:09.000")).toString());
    }
}
