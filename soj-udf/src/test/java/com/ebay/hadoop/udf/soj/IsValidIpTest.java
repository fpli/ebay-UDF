package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IsValidIpTest {

    @Test
    public void test() {
        IsValidIPv4 obj = new IsValidIPv4();
        assertEquals(new IntWritable(0), obj.evaluate(new Text("10..1.1")));
        assertEquals(new IntWritable(1), obj.evaluate(new Text("10.1.1.1")));
        assertEquals(new IntWritable(0), obj.evaluate(new Text("10.1.1.1.1")));
        assertEquals(new IntWritable(0), obj.evaluate(new Text("10.1.256.1")));
    }

}
