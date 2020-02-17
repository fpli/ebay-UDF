package com.ebay.carmel.udf.syslib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IsDecimalTest implements TestBase {

    @Test
    public void testUdf() throws Exception {
        IsDecimal isDecimal = new IsDecimal();
        assertEquals(1, isDecimal.evaluate("123456789012345678",38));
        assertEquals(1, isDecimal.evaluate("     123456789012345678    ",38));
        assertEquals(0, isDecimal.evaluate("     123456789 012345678    ",38));
        assertEquals(0, isDecimal.evaluate("     123456789012345678    ",10));
        assertEquals(0, isDecimal.evaluate("     123456789012345678    ",10));
        assertEquals(1, isDecimal.evaluate("     .123456789012345678  ",38));
        assertEquals(1, isDecimal.evaluate("     123456789.12345678  ",38));
        assertEquals(1, isDecimal.evaluate("     123456789012345678.  ",38));
        assertEquals(0, isDecimal.evaluate("    123456789.1234.5678  ",38));
        assertEquals(1, isDecimal.evaluate("    +123456789.12345678  ",38));
        assertEquals(1, isDecimal.evaluate("    -123456789.12345678  ",38));
        assertEquals(0, isDecimal.evaluate("    - 123456789.12345678  ",38));
        assertEquals(1, isDecimal.evaluate("   +999999999999999999.  ",38));
    }
}
