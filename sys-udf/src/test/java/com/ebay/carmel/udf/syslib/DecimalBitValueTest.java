package com.ebay.carmel.udf.syslib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DecimalBitValueTest implements TestBase {

    @Test
    public void testUdf() throws Exception {
        DecimalBitValue decimalBitValue = new DecimalBitValue();
        assertEquals(1, decimalBitValue.evaluate(15725685147225l, 29));
        assertEquals(0, decimalBitValue.evaluate(15725685147225l, 28));
        assertEquals(0, decimalBitValue.evaluate(15725685147225l, 4));
        assertEquals(1, decimalBitValue.evaluate(15725685147225l, 26));
        assertEquals(1, decimalBitValue.evaluate(15725685147225l, 64));
        assertEquals(0, decimalBitValue.evaluate(15725685147225l, 74));
        assertEquals(0, decimalBitValue.evaluate(15725685147225l, 0));
        assertEquals(0, decimalBitValue.evaluate(15725685147225l, 20));
        assertEquals(1, decimalBitValue.evaluate(15725685147225l, 21));
        assertEquals(1, decimalBitValue.evaluate(15725685147225l, 22));

    }
}
