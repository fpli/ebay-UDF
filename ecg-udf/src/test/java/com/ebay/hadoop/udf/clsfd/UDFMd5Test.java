package com.ebay.hadoop.udf.clsfd;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jianyahuang on 2019.02.26
 */

public class UDFMd5Test {
    @Test
    public void test(){

        assertEquals(new Text("902fbdd2b1df0c4f70b4a5d23525e932") , new UDFMd5().evaluate(new Text("ABC")));

    }
}
