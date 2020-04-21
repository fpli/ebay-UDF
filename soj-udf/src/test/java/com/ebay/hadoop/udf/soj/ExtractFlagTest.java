package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExtractFlagTest {

    @Test
    public void test() {
        ExtractFlag obj = new ExtractFlag();
        assertEquals(new IntWritable(1), obj.evaluate(new Text("1234"), 3));
        assertEquals(new IntWritable(0), obj.evaluate(new Text("1234"), 4));
        assertEquals(new IntWritable(0), obj.evaluate(new Text("AAAA"), 4));
        assertEquals(new IntWritable(1), obj.evaluate(new Text("aaaa"), 4));
        assertEquals(new IntWritable(0), obj.evaluate(null, 4));

        System.out.println(obj.evaluate(new Text("Agg2YCAAIhQA"), 20));
    }

}
