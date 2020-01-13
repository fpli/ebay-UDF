package com.ebay.hadoop.udf.soj;

import junit.framework.Assert;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.Text;
import org.junit.Test;


public class IsValidPrivateIPv4HiveTest {

    @Test
    public void test() {
    	IsValidPrivateIPv4Hive test = new IsValidPrivateIPv4Hive();
    	BooleanWritable ip = test.evaluate(new Text("10..1.1"));
        Assert.assertEquals(false,ip.get());
        ip = test.evaluate(new Text("10.10.1"));
        Assert.assertEquals(false,ip.get());
        ip = test.evaluate(new Text("10.10.1."));
        Assert.assertEquals(false,ip.get());
        ip = test.evaluate(new Text("10.10.1.1.1"));
        Assert.assertEquals(false,ip.get());
        ip = test.evaluate(new Text("10.10.1.1"));
        Assert.assertEquals(true,ip.get());
        ip = test.evaluate(new Text("10. 10.1.1"));
        Assert.assertEquals(true,ip.get());
        ip = test.evaluate(new Text("10.10 .1.1"));
        Assert.assertEquals(true,ip.get());
        ip = test.evaluate(new Text("10.256.1.1"));
        Assert.assertEquals(false,ip.get());
        ip = test.evaluate(new Text("11.1.1.1"));
        Assert.assertEquals(false,ip.get());
        ip = test.evaluate(new Text("192.1.1.1"));
        Assert.assertEquals(false,ip.get());
        ip = test.evaluate(new Text("191.168.1.1"));
        Assert.assertEquals(false,ip.get());
        ip = test.evaluate(new Text("193.168.1.1"));
        Assert.assertEquals(false,ip.get());
        ip = test.evaluate(new Text("192.167.1.1"));
        Assert.assertEquals(false,ip.get());
        ip = test.evaluate(new Text("192.168.1.1"));
        Assert.assertEquals(true,ip.get());
        ip = test.evaluate(new Text("192.167.1.1"));
        Assert.assertEquals(false,ip.get());
        ip = test.evaluate(new Text("172.15.1.1"));
        Assert.assertEquals(false,ip.get());
        ip = test.evaluate(new Text("172.16.1.1"));
        Assert.assertEquals(true,ip.get());
        ip = test.evaluate(new Text("172.17.1.1"));
        Assert.assertEquals(true,ip.get());
        ip = test.evaluate(new Text("172.30.1.1"));
        Assert.assertEquals(true,ip.get());
        ip = test.evaluate(new Text("172.31.1.1"));
        Assert.assertEquals(true,ip.get());
        ip = test.evaluate(new Text("172.32.1.1"));
        Assert.assertEquals(false,ip.get());
        ip = test.evaluate(new Text(""));
        Assert.assertEquals(false,ip.get());
        ip = test.evaluate(null);
        Assert.assertEquals(false,ip.get());
    }
}
