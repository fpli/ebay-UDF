package com.ebay.carmel.udf.syslib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IsIntegerTest implements TestBase {

    @Test
    public void testUdf() throws Exception {
        IsInteger isInteger = new IsInteger();
        assertEquals(1, isInteger.evaluate("    1234567890   "));
        assertEquals(0, isInteger.evaluate("12345678901"));
        assertEquals(0, isInteger.evaluate("    12345 67890  "));
        assertEquals(0, isInteger.evaluate("    12345A7890  "));
        assertEquals(1, isInteger.evaluate("    1234567890.  "));
        assertEquals(0, isInteger.evaluate("    123456.7890  "));
        assertEquals(0, isInteger.evaluate("    1234567890..  "));
        assertEquals(1, isInteger.evaluate("   +1234567890   "));
        assertEquals(0, isInteger.evaluate("  + 1234567890  "));
        assertEquals(0, isInteger.evaluate("   +2147483650 "));
        assertEquals(1, isInteger.evaluate("    -2147483648 "));
        assertEquals(0, isInteger.evaluate("    -2147483649 "));
    }
}
