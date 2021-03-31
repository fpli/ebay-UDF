package com.ebay.hadoop.udf.bx;

import com.ebay.hadoop.udf.bx.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

// TODO modify the UDFs to adhere java coding standards and elimate hardcoding and adhoc string builders

public class HPPopDestImprnClickExt extends UDF {


    private static class ModuleData {
        private String moduleId;
        private String iid;
        private String moduleData;

        public String getModuleId() {
            return moduleId;
        }

        public String getIid() {
            return iid;
        }

        public String getModuleData() {
            return moduleData;
        }

        public void setModuleId(String moduleId) {
            this.moduleId = moduleId;
        }

        public void setIid(String iid) {
            this.iid = iid;
        }

        public void setModuleData(String moduleData) {
            this.moduleData = moduleData;
        }
    }




    private static class ClickImprns {
        private Integer moduleId;
        private Integer iid;
        private Integer link;
        private Integer comp;
        private String  title;
        private Integer clicks;
        private Integer surfaces;

        public ClickImprns() {
            moduleId = -1;
            link = -1;
            comp = -1;
            iid = -1;
            title = "Unk" ;
            clicks = 0;
            surfaces =0;
        }

        public ClickImprns(Integer moduleId, Integer iid, Integer link, Integer comp, String title) {
            this.moduleId = moduleId;
            this.iid = iid;
            this.link = link;
            this.comp = comp;
            this.title = title ;
            this.clicks=0;
            this.surfaces=0;
        }

        public ClickImprns(Integer moduleId, Integer iid, Integer link, Integer comp) {
            this.moduleId = moduleId;
            this.iid = iid;
            this.link = link;
            this.comp = comp;
            this.title = "Unk" ;
            this.clicks=0;
            this.surfaces=0;
        }

        public ClickImprns(Integer moduleId, Integer link, Integer comp, Integer iid, Integer clicks, Integer surfaces) {
            this.moduleId = moduleId;
            this.iid = iid;
            this.link = link;
            this.comp = comp;
            this.title = "Unk" ;
            this.clicks = clicks;
            this.surfaces = surfaces;

        }

        public Integer getModuleId() {
            return moduleId;
        }

        public Integer getLink() {
            return link;
        }

        public Integer getComp() {
            return comp;
        }

        public String getTitle() {
            return title;
        }

        public Integer getIid() {
            return iid;
        }

        public Integer getClicks() {
            return clicks;
        }

        public Integer getSurfaces() {
            return surfaces;
        }

        public void setModuleId(Integer moduleId) {
            this.moduleId = moduleId;
        }

        public void setLink(Integer link) {
            this.link = link;
        }

        public void setComp(Integer comp) {
            this.comp = comp;
        }

        public void setIid(Integer iid) {
            this.iid = iid;
        }

        public void setClick(Integer clicks) {
            this.clicks = clicks;
        }

        public void setSurfaces(Integer surfaces) {
            this.surfaces = surfaces;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public void incrementClicks(Integer click) {
            this.clicks = this.clicks + click;
        }

        public void incrementSurfaces(Integer surface){
            this.surfaces = this.surfaces + surface;
        }


    }



    /* Matches the Moduledata pattern*/
    private static Pattern pattern = Pattern.compile("li:(.+)\\|luid:(.+)\\|c:(.+)\\|title:(.+)");



    /* Join the MODULE PLACEMENT DATA, MODULE CLICKS*/
    public List<Text> evaluate(final Map<Text, Text> modulePlacements, final List<Text> moduleClicks) throws Exception {



        // split the MODULE PLACEMENT DATA field into Lists
         Map<String,ClickImprns> finalResult = new HashMap<String,ClickImprns>();

        String dataValue;
        if(modulePlacements != null){

            for (Map.Entry<Text, Text> entry : modulePlacements.entrySet()) {
                dataValue = entry.getValue().toString();

                ModuleData[] modules = JsonUtils.fromJson(dataValue, ModuleData[].class);

                //Check if moduledata is NULL
                for (ModuleData moduledata : modules) {
                    if (moduledata.getModuleData() != null) {
                        String decodedModuleData = URLDecoder.decode(URLDecoder.decode(moduledata.getModuleData(), "UTF-8"), "UTF-8");
                        String[] moduleData = decodedModuleData.split(",");
                        for (String data : moduleData) {
                            Matcher m = pattern.matcher(data);
                            if (m.matches()) {

                                ClickImprns value = new ClickImprns(
                                        Integer.parseInt(moduledata.getModuleId())
                                        , Integer.parseInt(moduledata.getIid())
                                        , Integer.parseInt(m.group(1)) //Link
                                        , Integer.parseInt(m.group(3)) //Comp
                                        , m.group(4) //Title
                                );
                                value.incrementSurfaces(1);

                                finalResult.put( moduledata.getModuleId()+"_"+ moduledata.getIid()+"_"+m.group(3) , value );

                            }
                        }
                    }
                }


            }

        }



       //STEP 2
       //Parse the click data and PUSH into finalResult MAP

        if (moduleClicks != null) {

            for (Text module : moduleClicks) {

                String moduleClkStr = module.toString();
                if (StringUtils.isEmpty(moduleClkStr)) {
                    continue;
                }

                //Umesh : Modified the Split to use the function from common utils
                //2018-08-23
                String[] clickContents = StringUtil.split(moduleClkStr,".");
                String mod, link, comp, iid;

                mod = StringUtils.isNotBlank(clickContents[0]) ?
                        clickContents[0] : "-1";
                link = StringUtils.isNotBlank(clickContents[1]) ?
                        clickContents[1] : "-1";
                comp = StringUtils.isNotBlank(clickContents[2]) ?
                        clickContents[2] : "-1";
                iid = StringUtils.isNotBlank(clickContents[3]) ?
                        clickContents[3] : "-1";

                String key = mod + "_" + iid + "_" + comp;


                if (finalResult.containsKey(key)) {
                    ClickImprns cData = finalResult.get(key);
                    cData.incrementClicks(1);
                } else {
                    ClickImprns clkData = new ClickImprns(Integer.parseInt(mod), Integer.parseInt(iid), Integer.parseInt(link), Integer.parseInt(comp),"UNKNOWN");
//                    clkData.incrementSurfaces(1);
                    clkData.incrementClicks(1);
                    finalResult.put(key, clkData);
                }

            }
        }


        //STEP:3
        //Coverting the finalResult MAP into JSON String


        //Final result
        ArrayList<Text> finalResults = new ArrayList<Text>();

        ObjectMapper mapper = new ObjectMapper();

        for (Map.Entry<String, ClickImprns> entry : finalResult.entrySet()) {
            ClickImprns value = entry.getValue();
            String[] keys = entry.getKey().split("_");
            //String clickdata = keys[0] + ":" + keys[1] + ":" +  keys[2] + ":" +value.getLink() + ":" + value.getTitle() + ":" + value.getSurfaces() + ":" + value.getClicks();
            JsonNode node = mapper.createObjectNode();
            ObjectNode resultNode = ((ObjectNode) node).put("module_id",keys[0] );
            resultNode.put("module_id",keys[0] );
            resultNode.put("instance_id",keys[1] );
            resultNode.put("component_id",keys[2] );
            resultNode.put("link_id",value.getLink()  );
            resultNode.put("title_name",value.getTitle() );
            resultNode.put("imprsn",value.getSurfaces() );
            resultNode.put("clicks",value.getClicks() );


            finalResults.add(new Text(resultNode.toString()));
        }


        return finalResults;
    }




    public static void main(String args[]) throws Exception {


        HPPopDestImprnClickExt func = new HPPopDestImprnClickExt();
        Map testmap = new HashMap<>();

        testmap.put(new Text("4776_1"), new Text("[{\"moduleId\":\"4776\",\"iid\":\"1\",\"moduleData\":\"li%3A8874%7Cluid%3A0%7Cc%3A0%7Ctitle%3AWomen%25E2%2580%2599s%2BStyle%2Cli%3A8874%7Cluid%3A1%7Cc%3A1%7Ctitle%3AMen%25E2%2580%2599s%2BStyle%2Cli%3A8874%7Cluid%3A2%7Cc%3A2%7Ctitle%3AKitchen%2B%2526%2BBar%2Cli%3A8874%7Cluid%3A3%7Cc%3A3%7Ctitle%3AHome%2Cli%3A8874%7Cluid%3A4%7Cc%3A4%7Ctitle%3AOutdoors%2Cli%3A8874%7Cluid%3A5%7Cc%3A5%7Ctitle%3AMotors%2Cli%3A8874%7Cluid%3A6%7Cc%3A6%7Ctitle%3AArt%2B%2526%2BDesign\"}]"));
        //testmap.put(new Text("4776_2"), new Text("[{\"moduleId\":\"4776\",\"iid\":\"2\",\"moduleData\":\"li%3A8874%7Cluid%3A0%7Cc%3A0%7Ctitle%3AWomen%25E2%2580%2599s%2BStyle%2Cli%3A8874%7Cluid%3A1%7Cc%3A1%7Ctitle%3AMen%25E2%2580%2599s%2BStyle%2Cli%3A8874%7Cluid%3A2%7Cc%3A2%7Ctitle%3AKitchen%2B%2526%2BBar%2Cli%3A8874%7Cluid%3A3%7Cc%3A3%7Ctitle%3AHome%2Cli%3A8874%7Cluid%3A4%7Cc%3A4%7Ctitle%3AOutdoors%2Cli%3A8874%7Cluid%3A5%7Cc%3A5%7Ctitle%3AMotors%2Cli%3A8874%7Cluid%3A6%7Cc%3A6%7Ctitle%3AArt%2B%2526%2BDesign\"}]"));
        //testmap.put(new Text("4776_3"), new Text("[{\"moduleId\":\"4776\",\"iid\":\"3\",\"moduleData\":\"li%3A8874%7Cluid%3A0%7Cc%3A0%7Ctitle%3AWomen%25E2%2580%2599s%2BHandbag%2BDeals%2Cli%3A8874%7Cluid%3A1%7Cc%3A1%7Ctitle%3AMen%2527s%2BClothing%2BDeals%2Cli%3A8874%7Cluid%3A2%7Cc%3A2%7Ctitle%3ABright%2BPink%2Bfor%2BBreast%2BCancer%2Cli%3A8874%7Cluid%3A3%7Cc%3A3%7Ctitle%3ADeals%2Bon%2BToys%2Cli%3A8874%7Cluid%3A4%7Cc%3A4%7Ctitle%3ADyson%2BOutlet%2BCoupon%2Cli%3A8874%7Cluid%3A5%7Cc%3A5%7Ctitle%3ADeals%2Bon%2BWheels%2B%2526%2BTires%2Cli%3A8874%7Cluid%3A6%7Cc%3A6%7Ctitle%3AHalloween%2BCostumes\"}]"));
        //testmap.put(new Text("4776_2"), new Text("[{\"moduleId\":\"4776\",\"iid\":\"2\",\"moduleData\":\"li%3A8874%7Cluid%3A1%7Cc%3A1%7Ctitle%3AMen%2527s%2BMilitary%2BLook%2BJackets%2Cli%3A8874%7Cluid%3A2%7Cc%3A2%7Ctitle%3AMen%2527s%2BBomber%2BJackets%2Cli%3A8874%7Cluid%3A3%7Cc%3A3%7Ctitle%3ABackpacks%2Cli%3A8874%7Cluid%3A4%7Cc%3A4%7Ctitle%3ADorm%2BDecor%2BDeals%2Cli%3A8874%7Cluid%3A5%7Cc%3A5%7Ctitle%3ACollege%2BShopping%2BList%2Cli%3A8874%7Cluid%3A6%7Cc%3A6%7Ctitle%3ABargain%2BCars%2Cli%3A8874%7Cluid%3A7%7Cc%3A7%7Ctitle%3ABack%2Bto%2BCollege%2BGift%2BCards\"}]"));
        //testmap.put(new Text("4776_1"), new Text("[{\"moduleId\":\"4776\",\"iid\":\"1\",\"moduleData\":null}]"));

        ArrayList<Text> clicks = new ArrayList<Text>();
        //module,link,component,IID
        //clicks.add(new Text(""));
        clicks.add(new Text("4778.3456.."));

        List<Text> result1 = func.evaluate( testmap, clicks);

    }
}
