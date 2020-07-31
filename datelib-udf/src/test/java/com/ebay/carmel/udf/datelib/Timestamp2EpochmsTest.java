package com.ebay.carmel.udf.datelib;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class Timestamp2EpochmsTest {

    @Test
    public void test() throws ParseException {
        Timestamp2Epochms timestamp2Epochms = new Timestamp2Epochms();
        assertEquals("1584439929000", timestamp2Epochms.evaluate(new Text("2020-03-17 18:12:09.000")).toString());
    }
}
