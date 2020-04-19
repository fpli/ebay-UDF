package com.ebay.hadoop.udf.soj;

import junit.framework.Assert;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.junit.Test;


public class SOJBase64ToLongHiveTest {

    @Test
    public void test() {
    	SOJBase64ToLongHive test = new SOJBase64ToLongHive();
        LongWritable ip = test.evaluate(new Text("iIDWAAE*"));


        Assert.assertEquals(4309024904L,ip.get());
        ip = test.evaluate(new Text("9CuflM*"));
        Assert.assertEquals(null, ip);
    }
}
