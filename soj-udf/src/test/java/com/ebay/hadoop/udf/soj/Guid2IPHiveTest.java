package com.ebay.hadoop.udf.soj;

import junit.framework.Assert;
import org.apache.hadoop.io.Text;
import org.junit.Test;


public class Guid2IPHiveTest {

    @Test
    public void test() {
    	GUID2IPHive test = new  GUID2IPHive();
    	Text ip = test.evaluate(new Text("Guid2IPHiveTest"));
    	System.out.println(ip);
//        Assert.assertEquals("10.10.161.120", ip.toString());
        Assert.assertEquals(null, ip);

    }

    @Test
    public void testShortString() {
    	GUID2IPHive test = new  GUID2IPHive();
    	Text l = test.evaluate(new Text("bb78f16f13a0a0aa17865356ff92ee9"));
        Assert.assertEquals(null, l);
    }

    @Test
    public void testNullString() {
    	GUID2IPHive test = new  GUID2IPHive();
    	Text l = test.evaluate(null);
        Assert.assertEquals(null, l);
    }
}
