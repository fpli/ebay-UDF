package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IsDecimalTest {

    @Test
    public void test() {
        IsDecimal obj = new IsDecimal();
        assertEquals(new IntWritable(1), obj.evaluate(new Text("123456.123"), 10, 4));
        assertEquals(new IntWritable(0), obj.evaluate(new Text("123456.123"), 9, 4));
        assertEquals(new IntWritable(1), obj.evaluate(new Text("123456.123"), 11, 4));
        assertEquals(new IntWritable(0), obj.evaluate(new Text("123456.123"), 10, -1));
        assertEquals(new IntWritable(1), obj.evaluate(new Text("123456.123"), 9, 3));
        assertEquals(new IntWritable(0), obj.evaluate(new Text("486221E0"), 18, 0));
        assertEquals(new IntWritable(0), obj.evaluate(new Text("486221e0"), 12, 0));
    }

}
