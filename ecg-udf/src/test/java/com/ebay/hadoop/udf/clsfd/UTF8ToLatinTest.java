package com.ebay.hadoop.udf.clsfd;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jianyahuang on 2019.02.24
 */

public class UTF8ToLatinTest {
    
    @Test
    public void test(){
        String output = new UTF8ToLatin().evaluate("abc");
        System.out.print(output );
        assertEquals("abc" , output );
    }
}
