package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SOJExtractListNVPTest {

    @Test
    public void testPositive() {
        SojExtractListNVP obj = new SojExtractListNVP();
        List<String> expList = new ArrayList<>();
        expList.add("d9");
        expList.add("ss");
        expList.add("55");
        assertEquals("SojExtractNVP is working!", expList, obj.evaluate("h8=d3&h4=d9&h4=ss&aa=cc&h4=55&", "h4", "&", "="));
        expList.clear();
        expList.add("7024841724");
        expList.add("1234");
        assertEquals(expList, obj.evaluate("bn:7024841724|aa:cc|bn:1234|", "bn", "|", ":"));
        assertNull(obj.evaluate("bn:7024841724|aa:cc|bn:1234|", "bc", "|", ":"));

    }

}
