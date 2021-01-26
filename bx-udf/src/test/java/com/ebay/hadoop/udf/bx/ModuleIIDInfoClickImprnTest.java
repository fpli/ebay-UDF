package com.ebay.hadoop.udf.bx;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by umohan on 3/30/18.
 */

public class ModuleIIDInfoClickImprnTest {


    private ArrayList<Text> surfaces = new ArrayList<>();
    private ArrayList<Text> clicks = new ArrayList<>();
    private Map<Text,Text> placements = new HashMap<>();
    private ArrayList<Text> views = new ArrayList<>();
    private ArrayList<Text> hscrolls = new ArrayList<>();


    /**Test of 4 inputs for module:5241 */
    @Test
    public void testFourInputsV1() throws Exception {

        surfaces.add(new Text("5241:RIVER:1:0:1"));
        clicks.add(new Text("5241.3949.1.1"));
        views.add(new Text("mi:5241|iid:1|dur:16"));
        hscrolls.add(new Text("mi:5241|iid:1"));
        placements.put(new Text("5241_1"), new Text("[{\"moduleId\":\"5241\",\"iid\":\"1\",\"moduleData\":\"interestType%3Aexplicit\"}]"));

        List<String> expOutValue = Arrays.asList("5241.3949.1.1:RIVER:1:0:1:1:0:0:explicit:0", "5241...1:RIVER:1:0:1:0:1:1:explicit:1");
        ModuleIIDInfoClickImprn moduleOutput = new ModuleIIDInfoClickImprn();
        assertEquals(expOutValue.toString(), moduleOutput.evaluate(placements,surfaces, clicks,views,hscrolls).toString());

    }


    /**Test of 4 inputs for module:4776 */
    @Test
    public void testFourInputsV2() throws Exception {

        surfaces.add(new Text("4776:RIVER:1:0:1"));
        clicks.add(new Text("4776.3949.1.1"));
        views.add(new Text("{\"moduleInstance\":\"mi:4776|iid:1\",\"parentrq\":\"c0a2f5e41620ab1d9e46bfcdfffcf19f\",\"trackViews\":true,\"trackPerf\":false},{\"moduleInstance\":\"mi:4817|iid:1\",\"parentrq\":\"c0a2f5e41620ab1d9e46bfcdfffcf19f\",\"trackViews\":true,\"trackPerf\":false},{\"moduleInstance\":\"mi:4518|iid:0\",\"parentrq\":\"c0a2f5e41620ab1d9e46bfcdfffcf19f\",\"trackViews\":true,\"trackPerf\":false},{\"moduleInstance\":\"mi:4816|iid:1\",\"parentrq\":\"c0a2f5e41620ab1d9e46bfcdfffcf19f\",\"trackViews\":true,\"trackPerf\":false}"));
        placements.put(new Text("4776_1"), new Text("[{\"moduleId\":\"4776\",\"iid\":\"1\",\"moduleData\":\"interestType%3Astatic\"}]"));
        hscrolls.add(new Text("mi:4776|iid:1"));

        List<String> expOutValue = Arrays.asList("4776.3949.1.1:RIVER:1:0:1:1:0:0:NA:0", "4776...1:RIVER:1:0:1:0:1:1:NA:1");
        ModuleIIDInfoClickImprn moduleOutput = new ModuleIIDInfoClickImprn();
        assertEquals(expOutValue.toString(), moduleOutput.evaluate(placements,surfaces, clicks,views,hscrolls).toString());

    }



    /**Test of 4 inputs for exceptions */
    @Test
    public void testFourInputsWithException() throws Exception {

        surfaces.add(new Text("4776:RIVER:1:0:1"));
        clicks.add(new Text("4776.3949.1.1"));
        views.add(new Text("{\"moduleInstance\":\"mi:4776:iid:1\",\"parentrq\":\"c0a2f5e41620ab1d9e46bfcdfffcf19f\",\"trackViews\":true,\"trackPerf\":false},{\"moduleInstance\":\"mi:4817|iid:1\",\"parentrq\":\"c0a2f5e41620ab1d9e46bfcdfffcf19f\",\"trackViews\":true,\"trackPerf\":false},{\"moduleInstance\":\"mi:4518|iid:0\",\"parentrq\":\"c0a2f5e41620ab1d9e46bfcdfffcf19f\",\"trackViews\":true,\"trackPerf\":false},{\"moduleInstance\":\"mi:4816|iid:1\",\"parentrq\":\"c0a2f5e41620ab1d9e46bfcdfffcf19f\",\"trackViews\":true,\"trackPerf\":false}"));
        placements.put(new Text("4776_1"), new Text("[{\"moduleId\":\"4776\",\"iid\":\"1\",\"moduleData\":\"interestType%3Astatic\"}]"));
        hscrolls.add(new Text("mi:4776|iid:1"));

        List<String> expOutValue = Arrays.asList("4776.3949.1.1:RIVER:1:0:1:1:0:0:NA:0", "4776...1:RIVER:1:0:1:0:1:0:NA:1");
        ModuleIIDInfoClickImprn moduleOutput = new ModuleIIDInfoClickImprn();
        assertEquals(expOutValue.toString(), moduleOutput.evaluate(placements,surfaces, clicks,views,hscrolls).toString());


    }




    /**Test of 2 inputs */
    @Test
    public void testTwoInputs() throws Exception {
        surfaces.add(new Text("4497:RIVER:8:1:2"));
        surfaces.add(new Text("5241:RIVER:1:0:1"));
        clicks.add(new Text("5241.3949.1.1"));

        List<String> expOutValue = Arrays.asList("4497...2:RIVER:8:1:2:0:1","5241.3949.1.1:RIVER:1:0:1:1:0","5241...1:RIVER:1:0:1:0:1");
        ModuleIIDInfoClickImprn moduleOutput = new ModuleIIDInfoClickImprn();
        assertEquals(expOutValue.toString(), moduleOutput.evaluate(surfaces, clicks).toString());


    }



    /**Test of 4 inputs for null values */
    @Test
    public void testFourInputsWithNull() throws Exception {

        surfaces.add(new Text());
        clicks.add(new Text());
        views.add(new Text());
        placements.put(new Text(), new Text());
        hscrolls.add(new Text());

        List<String> expOutValue = Arrays.asList("");
        ModuleIIDInfoClickImprn moduleOutput = new ModuleIIDInfoClickImprn();
        assertEquals(expOutValue.toString(), moduleOutput.evaluate(placements,surfaces, clicks,views,hscrolls).toString());


    }

}