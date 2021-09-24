package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SOJExtractNVPTest {

    @Test
    public void testPositive() {
        SojExtractNVP obj = new SojExtractNVP();
        assertEquals("SojExtractNVP is working!", new Text("d9"), obj.evaluate("h8=d3&h4=d9&", "h4", "&", "="));
        assertEquals(new Text("7024841724"), obj.evaluate("bn:7024841724|", "bn", "|", ":"));
        assertEquals(new Text("Admin"), obj.evaluate(",objective:Admin", "objective", ",", ":"));

    }

}
