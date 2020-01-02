package com.ebay.hadoop.udf.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Created by jacshen on 6/29/2017.
 */
public class D8IdObsecureTest {

    @Test
    public void test() {
        Long input = 1l;
        assertEquals(Long.valueOf(43188348268l), new D8IdObscure().evaluate(input));
        input = -1l;
        assertEquals(Long.valueOf(-43188348270l), new D8IdObscure().evaluate(input));
    }
}