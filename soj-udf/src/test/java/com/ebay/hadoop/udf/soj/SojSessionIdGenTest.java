package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SojSessionIdGenTest {

    @Test
    public void evaluate() throws Exception {
        assertEquals(new Text("0000000000000001"), new SojSessionIdGen().evaluate(null, 1L));
        assertEquals(new Text("abc0000000000000000"), new SojSessionIdGen().evaluate("abc", 0L));
        assertEquals(new Text("abcffffffffffffff91"), new SojSessionIdGen().evaluate("abc", -111));
        assertEquals(new Text("691e44aff3774286830b8e2c1f7338e200000184467cc86e"),
                new SojSessionIdGen().evaluate("691e44aff3774286830b8e2c1f7338e2", 1667629893742L));
    }
}
