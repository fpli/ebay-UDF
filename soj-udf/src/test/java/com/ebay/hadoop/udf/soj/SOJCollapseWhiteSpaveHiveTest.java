package com.ebay.hadoop.udf.soj;

import junit.framework.Assert;
import org.apache.hadoop.io.Text;
import org.junit.Test;

public class SOJCollapseWhiteSpaveHiveTest {

    @Test
    public void test() {
    	SOJCollapseWhiteSpaceHive test = new SOJCollapseWhiteSpaceHive();
        Text value = test.evaluate(new Text("test    string")); 
        Assert.assertEquals("test string",value.toString());
    }

    @Test
    public void testLeadingSpaces() {
    	SOJCollapseWhiteSpaceHive test = new SOJCollapseWhiteSpaceHive();
    	Text value = test.evaluate(new Text("    test    string")); 
        Assert.assertEquals(" test string",value.toString());
        
        value = test.evaluate(new Text(" test    string")); 
        Assert.assertEquals(" test string",value.toString());
    }

    @Test
    public void testTailingSpaces() {
    	SOJCollapseWhiteSpaceHive test = new SOJCollapseWhiteSpaceHive();
    	Text value = test.evaluate(new Text("test    string    ")); 
        Assert.assertEquals("test string ",value.toString());
        
        value = test.evaluate(new Text("test    string ")); 
        Assert.assertEquals("test string ",value.toString());
    }
    
    @Test
    public void testNull() {
    	SOJCollapseWhiteSpaceHive test = new SOJCollapseWhiteSpaceHive();
    	Text value = test.evaluate(null); 
        Assert.assertEquals(null,value);
    }
    
    @Test
    public void testBlank() {
    	SOJCollapseWhiteSpaceHive test = new SOJCollapseWhiteSpaceHive();
    	Text value = test.evaluate(new Text("")); 
        Assert.assertEquals(null,value);
    }
}
