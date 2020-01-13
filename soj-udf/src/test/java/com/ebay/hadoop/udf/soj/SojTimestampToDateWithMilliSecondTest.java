package com.ebay.hadoop.udf.soj;

import junit.framework.Assert;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.junit.Test;

public class SojTimestampToDateWithMilliSecondTest {

    @Test
    public void test() throws Exception {
        SojTimestampToDateWithMilliSecond udf = new SojTimestampToDateWithMilliSecond();
        Assert.assertEquals(new Text("2013/08/21 23:49:28.123"), udf.evaluate(new LongWritable(3586117768123000L)));
    }
}
