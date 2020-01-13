package com.ebay.hadoop.udf.soj;

import junit.framework.Assert;
import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.net.URLEncoder;


public class SOJURLDecodeEscapeHiveTest {

	  @Test
	  public void test() throws Exception{
		SOJURLDecodeEscapeHive test = new SOJURLDecodeEscapeHive();
	    Text value = test.evaluate(new Text("/item%2Fval1%E2"), "%");
	    Assert.assertEquals("/item/val1â", value.toString());
	    value = test.evaluate(new Text("/item%2Fval1%FH"), "%");
	    Assert.assertEquals("/item/val1%FH", value.toString());
	    value = test.evaluate(new Text(""), "%");
	    Assert.assertEquals(null, value);
	    value = test.evaluate(null, "%");
	    Assert.assertEquals(null, value);
	    value = test.evaluate(null, null);
	    Assert.assertEquals(null, value);
	  }

	  @Test
	  public void test1() throws Exception{
		SOJURLDecodeEscapeHive test = new SOJURLDecodeEscapeHive();
	    //decode utf8
	    String source = "k=中国";
	    String target = URLEncoder.encode(source, "UTF-8");
	    Assert.assertEquals(source, test.evaluate(new Text(target), "UTF-8").toString());
	    //decode last wrong escape char
	    source = "/a/b/";
	    target = URLEncoder.encode(source, "UTF-8");
	    target = target.substring(0,target.length() -1);
	    Assert.assertEquals("/a/b%2", test.evaluate(new Text(target), "UTF-8").toString());
	  }
}
