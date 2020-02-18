package com.ebay.carmel.udf.syslib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IsNumberTest implements TestBase {

    @Test
    public void testUdf() throws Exception {
        IsNumber isNumber = new IsNumber();
        assertEquals(0, isNumber.evaluate("   12345678901234567.90123456780 "));
        assertEquals(0, isNumber.evaluate("   +12345678901234567890123456780 "));
        assertEquals(0, isNumber.evaluate("   + 12345678901234567890123456780 "));
        assertEquals(0, isNumber.evaluate("   -12345678901234567890123456780 "));
        assertEquals(0, isNumber.evaluate("   12345678901234567A90123456780 "));
        assertEquals(1, isNumber.evaluate("   12345678901234567890123456780 "));
    }
}
