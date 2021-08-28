package com.ebay.carmel.udf.datelib;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.text.ParseException;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;


public class Date2EpochTest extends DateTestBase {
    @Test
    public void test() throws Exception {
        withUtcTimeZone(() -> {
            Date2Epoch date2Epoch = new Date2Epoch();
            assertEquals("1584403200000", date2Epoch.evaluate(new Text("2020-03-17")).toString());
            return null;
        });
    }
}
