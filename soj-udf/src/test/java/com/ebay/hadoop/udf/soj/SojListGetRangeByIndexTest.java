package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SojListGetRangeByIndexTest {

    @Test
    public void test() {
        SojListGetRangeByIndex obj = new SojListGetRangeByIndex();
        assertEquals(new Text("1.2"), obj.evaluate(new Text("1.2;"),";",1,-1));

    }

}
