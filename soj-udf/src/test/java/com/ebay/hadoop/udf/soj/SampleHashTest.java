package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SampleHashTest {

    @Test
    public void evaluate() throws Exception {
        assertEquals(null, new SampleHash().evaluate(null,1));
        assertEquals(null, new SampleHash().evaluate("abc",0));

        assertEquals(Integer.valueOf(1), new SampleHash().evaluate("5e525f491310a5a9631335c3ff79ba26",100));
        assertEquals(Integer.valueOf(7), new SampleHash().evaluate("5cd28ffd1310a026b3821322ffcd6c36",100));
        assertEquals(Integer.valueOf(9), new SampleHash().evaluate("5e1005c71310a47a2d9243e2ffd42109",100));
        assertEquals(Integer.valueOf(7), new SampleHash().evaluate("5e680f831310a5a8e1c5fff5ffa7def9",100));
        assertEquals(Integer.valueOf(5), new SampleHash().evaluate("5bcd064b1310a0b58d964bb6ffe157f5",100));
        assertEquals(Integer.valueOf(7), new SampleHash().evaluate("5ec6ebd01310a03662461686ffbd9bd3",100));
        assertEquals(Integer.valueOf(9), new SampleHash().evaluate("5ebfa41f1310a036691544d5fffa1023",100));
        assertEquals(Integer.valueOf(138), new SampleHash().evaluate("5ebfa41f1310a036691544d5fffa1023",999));
    }
}