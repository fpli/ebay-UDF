package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.io.IntWritable;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IsTimestampTest {
    @Test
    public void test() {
        assertEquals(new IntWritable(1),new IsTimestamp().evaluate("2009-01-01 10:00:02",0));
        assertEquals(new IntWritable(1),new IsTimestamp().evaluate("2009-01-01 10:00:02",3));
        assertEquals(new IntWritable(0),new IsTimestamp().evaluate("2009-01-01 10:00:02.123",0));
        assertEquals(new IntWritable(0),new IsTimestamp().evaluate("2009-01-01 10:00:02.123",2));
        assertEquals(new IntWritable(1),new IsTimestamp().evaluate("2009-01-01 10:00:02.123",4));
        assertEquals(new IntWritable(1),new IsTimestamp().evaluate("2009-01-01 10:00:02.1200",4));
        assertEquals(new IntWritable(1),new IsTimestamp().evaluate("2009-01-01 10:00:02",2));
        assertEquals(new IntWritable(0),new IsTimestamp().evaluate("2009/01/01 10:00:02",0));
    }
}