package com.ebay.carmel.udf.datelib;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WeekEndDateTest {

    @Test
    public void test() {
        WeekEndDate weekEndDate = new WeekEndDate();
        assertEquals("2020-03-21", weekEndDate.evaluate(new Text("2020-03-17")).toString());
        assertEquals("2020-03-21", weekEndDate.evaluate(new Text("2020-03-17 12:13:59")).toString());
        assertEquals("2020-03-21", weekEndDate.evaluate(new Text("1584437")).toString());

    }
}
