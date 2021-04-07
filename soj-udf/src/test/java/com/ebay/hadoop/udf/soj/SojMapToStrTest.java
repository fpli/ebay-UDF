package com.ebay.hadoop.udf.soj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Test;

public class SojMapToStrTest {

    @Test
    public void evaluateInOrder() throws Exception {
        Map<String,String> sojMap = new LinkedHashMap<>();
        sojMap.put("rdt","1");
        sojMap.put("Encoding","gzip, deflate");

        assertEquals("rdt=1&Encoding=gzip, deflate", new SojMapToStr().evaluate(
                sojMap  ).toString());

    }
    @Test
    public void evaluateOutOfOrder() throws Exception {
        Map<String,String> sojMap = new LinkedHashMap<>();
        sojMap.put("Encoding","gzip, deflate");
        sojMap.put("rdt","1");

        assertNotEquals("rdt=1&Encoding=gzip, deflate", new SojMapToStr().evaluate(
                sojMap  ).toString());

    }
}
