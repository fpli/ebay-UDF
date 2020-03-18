package com.ebay.carmel.udf.datelib;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;


public class Date2EpochTest {
    @Test
    public void test() throws ParseException {
        Date2Epoch date2Epoch = new Date2Epoch();
        assertEquals("1584374400000", date2Epoch.evaluate(new Text("2020-03-17")).toString());
    }
}
