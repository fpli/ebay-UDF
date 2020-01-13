package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.io.Text;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;


public class GUID2DateHiveTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetDate()  throws Exception{
        String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
        //use SimpleDateFormat to format the output
        //set TimeZone to "GMT" align with TD udf
        SimpleDateFormat timestampFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
        timestampFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        GUID2DateHive test = new GUID2DateHive();
        Text l = test.evaluate(new Text("bb78f16f13a0a0aa17865356ff92ee92"));
        assertEquals("2012-11-01 10:16:36.463", l.toString());
    }

    @Test
    public void testShortString()  throws Exception{
        GUID2DateHive test = new GUID2DateHive();
    	Text l=test.evaluate(new Text("bb78f16f13a0a0aa17865356ff92ee9"));
        assertEquals(null, l);
    }

//    @Test(expected= RuntimeException.class)
//    public void testNullString() throws Exception{
//        GUID2DateHive test = new GUID2DateHive();
//    	test.evaluate(null);
//    }
}
