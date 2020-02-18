package com.ebay.carmel.udf.syslib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntegerBitValueTest implements TestBase {
    @Test
    public void testUdf() throws Exception {
        IntegerBitValue integerBitValue = new IntegerBitValue();
        assertEquals(1, integerBitValue.evaluate(85, 26));
        assertEquals(1, integerBitValue.evaluate(85, 32));
        assertEquals(0, integerBitValue.evaluate(85, 0));
        assertEquals(0, integerBitValue.evaluate(85, 5));
        assertEquals(0, integerBitValue.evaluate(85, 60));
    }
}
