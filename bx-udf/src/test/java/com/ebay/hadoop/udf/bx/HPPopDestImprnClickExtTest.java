package com.ebay.hadoop.udf.bx;

import junit.framework.Assert;
import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HPPopDestImprnClickExtTest {


    private static HPPopDestImprnClickExt hpPopDestImprnClickExt = new HPPopDestImprnClickExt();


    @Test
    public void testModuleDataWithNull() throws Exception {

        Map modPlmt = new HashMap();
        ArrayList<Text> clicks = new ArrayList<Text>();

        modPlmt.put(new Text("5241_1"), new Text("[{\"moduleId\":\"4776\",\"iid\":\"1\",\"moduleData\":null}]"));
        clicks.add(new Text("5241.3949.1.1"));
        List<Text> result =  hpPopDestImprnClickExt.evaluate(modPlmt,clicks);
        Assert.assertEquals(1,result.size());

    }


    //  Test to get all the components
    @Test
    public void testModuleDataWithStr() throws Exception {

        Map modPlmt = new HashMap();
        ArrayList<Text> clicks = new ArrayList<Text>();

        modPlmt.put(new Text("4776_1"), new Text("[{\"moduleId\":\"4776\",\"iid\":\"1\",\"moduleData\":\"li%3A8874%7Cluid%3A0%7Cc%3A0%7Ctitle%3AWomen%25E2%2580%2599s%2BStyle%2Cli%3A8874%7Cluid%3A1%7Cc%3A1%7Ctitle%3AMen%25E2%2580%2599s%2BStyle%2Cli%3A8874%7Cluid%3A2%7Cc%3A2%7Ctitle%3AKitchen%2B%2526%2BBar%2Cli%3A8874%7Cluid%3A3%7Cc%3A3%7Ctitle%3AHome%2Cli%3A8874%7Cluid%3A4%7Cc%3A4%7Ctitle%3AOutdoors%2Cli%3A8874%7Cluid%3A5%7Cc%3A5%7Ctitle%3AMotors%2Cli%3A8874%7Cluid%3A6%7Cc%3A6%7Ctitle%3AArt%2B%2526%2BDesign\"}]"));
        clicks.add(new Text("4776.3949.1.1"));
        List<Text> result =  hpPopDestImprnClickExt.evaluate(modPlmt,clicks);
        Assert.assertEquals(7,result.size());

    }







}