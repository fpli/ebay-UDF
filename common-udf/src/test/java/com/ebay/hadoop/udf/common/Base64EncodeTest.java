package com.ebay.hadoop.udf.common;


import org.apache.hadoop.io.Text;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jacshen on 6/29/2017.
 */
public class Base64EncodeTest {

    @Test
    public void test() {
        String input = "original text!";
        assertEquals(new Text("b3JpZ2luYWwgdGV4dCEA"), new Base64Encode().evaluate(new Text(input)));
    }

}