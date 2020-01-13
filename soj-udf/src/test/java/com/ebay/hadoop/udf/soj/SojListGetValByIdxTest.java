package com.ebay.hadoop.udf.soj;

import junit.framework.Assert;
import org.apache.hadoop.io.Text;
import org.junit.Test;

public class SojListGetValByIdxTest {

	 @Test
	    public void test() throws Exception {
		 SojListGetValByIdx udf = new SojListGetValByIdx();
	        Assert.assertEquals(new Text("defc"), udf.evaluate(new Text("abc,defc,hef"),",",2));
	    }
	 
	 @Test
	    public void testWithInvalidIdx() throws Exception {
		 SojListGetValByIdx udf = new SojListGetValByIdx();
	        Assert.assertEquals(null, udf.evaluate(new Text("abc,defc,hef"),",",0));
	    }
}

