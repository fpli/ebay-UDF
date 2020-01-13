package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by szang on 7/31/17.
 */
public class IsDecimalTest {
    @Test
    public void evaluate() throws Exception {

        assertEquals(false, new IsDecimal().evaluate(null, 10));
        assertEquals(false, new IsDecimal().evaluate("", 10));
        assertEquals(false, new IsDecimal().evaluate("x.099", 10));
        assertEquals(false, new IsDecimal().evaluate("0.099.0", 10));
        assertEquals(false, new IsDecimal().evaluate("4.3xabc", 10));
        assertEquals(false, new IsDecimal().evaluate("4.33333333333", 10));
        assertEquals(true, new IsDecimal().evaluate("      0.123456             ", 18));
        assertEquals(true, new IsDecimal().evaluate("      -0.123456             ", 18));
        assertEquals(false, new IsDecimal().evaluate("-0.123-456", 10));
        assertEquals(false, new IsDecimal().evaluate("-+0.123-456", 10));
        assertEquals(false, new IsDecimal().evaluate("-0.1abc-456", 10));
        assertEquals(true, new IsDecimal().evaluate("0.123456", 10));
        assertEquals(true, new IsDecimal().evaluate("+0.123456", 10));
        assertEquals(true, new IsDecimal().evaluate("-0.123456", 10));
        assertEquals(true, new IsDecimal().evaluate(".099", 10));
        assertEquals(true, new IsDecimal().evaluate("555.", 10));

        assertEquals(true, new IsDecimal().evaluate("      -0.123456             "));
        assertEquals(false, new IsDecimal().evaluate("-0.1abc-456"));

    }

}