package com.ebay.carmel.udf.datelib;

import org.apache.hadoop.io.Text;
import org.junit.Test;
import parquet.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class WeekStartDateTest {

    @Test
    public void test() {
        WeekStartDate weekStartDate = new WeekStartDate();
        assertEquals("2020-03-15", weekStartDate.evaluate(new Text("2020-03-17")).toString());
        assertEquals("2020-03-15", weekStartDate.evaluate(new Text("2020-03-17 12:13:59")).toString());
        assertEquals("2020-03-15", weekStartDate.evaluate(new Text("1584374")).toString());
    }
}
