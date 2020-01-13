package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IsIntegerTest {

    @Test
    public void test() {
        IsInteger obj = new IsInteger();
        assertEquals(new IntWritable(1), obj.evaluate(new Text("234561")));
        assertEquals(new IntWritable(0), obj.evaluate(new Text("3.1415926")));
        assertEquals(new IntWritable(0), obj.evaluate(new Text("12345E2")));
        assertEquals(new IntWritable(0), obj.evaluate(new Text("12345e2")));
    }

}
