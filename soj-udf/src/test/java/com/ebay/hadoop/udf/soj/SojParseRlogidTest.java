package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.io.Text;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SojParseRlogidTest {
    @Test
    public void evaluate(){

        SojParseRlogid sojParseRlogid = new SojParseRlogid();
        assertEquals(new Text("slc4b01c-97737d"), sojParseRlogid.evaluate("t6pwehq%60%3C%3Dpie3a57d.%3C1002b-1397389e1f0-0xbc", "MacHine"));
        assertEquals(new Text("SLC"), sojParseRlogid.evaluate("t6pwehq%60%3C%3Dpie3a57d.%3C1002b-1397389e1f0-0xbc", "datacenter"));
        assertEquals(new Text("r1srcore"), sojParseRlogid.evaluate("t6pwehq%60%3C%3Dpie3a57d.%3C1002b-1397389e1f0-0xbc", "pool"));
        assertEquals(new Text("slc4b01c-97737d"), sojParseRlogid.evaluate("t6pwehq%60%3C%3Dpie3a57d.%3C1002b-1397389e1f0-0xbc", "machine"));
        assertEquals(new Text("2012-08-29 10:59:39"), sojParseRlogid.evaluate("t6pwehq%60%3C%3Dpie3a57d.%3C1002b-1397389e1f0-0xbc", "timestamp"));
        assertEquals(new Text("0xbc"), sojParseRlogid.evaluate("t6pwehq%60%3C%3Dpie3a57d.%3C1002b-1397389e1f0-0xbc", "THREADID"));
        assertEquals(new Text("r1srcore::slc4b01c-97737d"), sojParseRlogid.evaluate("t6pwehq%60%3C%3Dpie3a57d.%3C1002b-1397389e1f0-0xbc", "poolmachine"));


        assertNull(sojParseRlogid.evaluate("t6pwehq%60%3C%3Dpie3a57d.%3C1002b-0xbc", "THREADID"));
        assertNull(sojParseRlogid.evaluate("t6pwehq%60%3C%3Dpie3a57d.%3C1002b-1397389e1f0", "THREADID"));
        assertNull(sojParseRlogid.evaluate("1397389e1f0-0xbc", "THREADID"));

        assertNull(sojParseRlogid.evaluate("t6pwehq%60%3C%3Dpie3a57d.%3C1002b-1397389e1f0-0xbc", "ine"));

        assertNull(sojParseRlogid.evaluate(" ", "THREADID"));
        assertNull(sojParseRlogid.evaluate("", "THREADID"));
        assertNull(sojParseRlogid.evaluate(null, "THREADID"));


        assertNull(sojParseRlogid.evaluate("t6pwehq%60%3C%3Dpie3a57d.%3C1002b-1397389e1f0-0xbc", " "));
        assertNull(sojParseRlogid.evaluate("t6pwehq%60%3C%3Dpie3a57d.%3C1002b-1397389e1f0-0xbc", ""));
        assertNull(sojParseRlogid.evaluate("t6pwehq%60%3C%3Dpie3a57d.%3C1002b-1397389e1f0-0xbc", null));

    }
}
