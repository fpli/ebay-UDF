package com.ebay.hadoop.udf.bx;

import com.ebay.hadoop.udf.bx.constant.ApplicationConstants;
import com.ebay.hadoop.udf.bx.model.ModulePlmtImprns;
import com.ebay.hadoop.udf.bx.util.JsonUtils;
import com.ebay.hadoop.udf.bx.exception.JsonConvertException;
import com.ebay.hadoop.udf.bx.constant.Tags;
import com.ebay.hadoop.udf.bx.util.SOJExtractNVP;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**=
 * 2018-03-15
 * This UDF has been extended to take a new value "modulePlacements" and check its moduledata tag.
 * if the module=5241 and moduledata contains "interestType:static" , then its pre-onboarding module
 * 2018-04-20
 * The UDF has been extened to take the new value "Viewdetails" and use it in the final output
 * <p>
 * Output format :
 * "5241.3949.1.1:RIVER:1:0:1:2:0:0:explicit"
 * (module.link.component.instance):Region:Rank:IsCurated:Iid:Clicks:Surfaces:Views:intrstType:Hscrolls
 */


public class ModuleIIDInfoClickImprn extends UDF {

    private static final String MODULE_ID = "5241";
    private static final String VALUE_DELIM = ":";

    private ArrayList<Text> moduleClickImprns = new ArrayList<>();
    private Map<String, ClickSurfaces> modInfoClickImprnMap = new HashMap<>();
    private Map<String, String> modPlmtMap = new HashMap<>();
    private Set<String> modViewSet = new HashSet<>();
    private Set<String> modHscrollSet = new HashSet<>();

    /*
         parse module string
         @item  module item
         @type  symbol | means key:value   symbol . means value string
     */
    private String getModuleStr(String item,String type) {
        String key = "";

        if (!item.isEmpty() && !item.equals("")) {
            if(type.equals("|")){
                String mod = SOJExtractNVP.getTagValue(item, "mi", "\\|", ":");
                String iid = SOJExtractNVP.getTagValue(item, "iid", "\\|", ":");
                key = mod + "..." + iid;
            }else{
                String[] modContents = item.split(".");
                String mod = modContents[0] + "...";
                String iid = modContents[3];
                key = mod + iid;
            }

        }
        return key;

    }

    private Boolean checkTagNotNull(String item, String tag) {
        return SOJExtractNVP.getTagValue(item, tag, "\\|", ":") != null;
    }

    private List<String> convertStrToList(String moduleStr){
        List<String> items = Arrays.asList(moduleStr.replace("[", "").replace("]", "").split("\\s*,\\s*"));
        return items;
    }

    private StringBuffer convertModuleStr(String module, ClickSurfaces clickSurfaces){
        StringBuffer str = new StringBuffer();
        str = str.append(module).append(VALUE_DELIM)
                .append(clickSurfaces.getRegion()).append(VALUE_DELIM)
                .append(clickSurfaces.getRank()).append(VALUE_DELIM)
                .append(clickSurfaces.getIsCurated()).append(VALUE_DELIM)
                .append(clickSurfaces.getIid()).append(VALUE_DELIM)
                .append(clickSurfaces.getClicks()).append(VALUE_DELIM)
                .append(clickSurfaces.getSurfaces());
        return str;
    }
    /**
     * Common logic is enclosed within this method
     **/
    public void modifyClicks(ArrayList<Text> moduleInfos, ArrayList<Text> moduleClicks, ArrayList<Text> moduleViewsIn, ArrayList<Text> moduleHscrollIn) {

        /** flush the varirables **/
        modInfoClickImprnMap.clear();
        moduleClickImprns.clear();
        modViewSet.clear();
        modHscrollSet.clear();

        /** Flat map the input MODULEVIEWS */
        ArrayList<Text> moduleViews = new ArrayList<>();
        String moduleViewsInStr="";
        if(moduleViewsIn!=null){
            moduleViewsInStr = moduleViewsIn.toString();
        }

        if (moduleViewsInStr.contains(Tags.MODULEINSTANCE)) {
            Pattern pattern = Pattern.compile("\\{.*?\\}");
            Matcher matcher = pattern.matcher(moduleViewsInStr);

            while (matcher.find()) {
                // Group the matching string based om the brackets and flatmap
                String match = matcher.group(0);
                moduleViews.add(new Text(match));
            }
        } else {
            List<String> items = this.convertStrToList(moduleViewsInStr);

            for (String item : items) {
                moduleViews.add(new Text(item));
            }
        }


        //Load all module views to set "modViewSet" . Parsing logic is different for dWEb and Native
        ObjectMapper mapper = new ObjectMapper();

        if (moduleViews != null) {
            for (Text modView : moduleViews) {
                String modViewStr = modView.toString();

                if (!modViewStr.isEmpty() && !modView.equals("")) {

                    // dWeb - Viewdetails
                    if (modViewStr.contains(Tags.MODULEINSTANCE)) {
                        try {
                            JsonNode jsonData = mapper.readTree(modView.toString());
                            String intrstStatus = "";

                            if (jsonData.has(Tags.MODULEINSTANCE)) {

                                try {
                                    intrstStatus = URLDecoder.decode(URLDecoder.decode(jsonData.get(Tags.MODULEINSTANCE).asText(), ApplicationConstants.UTF8), ApplicationConstants.UTF8);
                                } catch (Exception e) {

                                }


                                //To take care of all the exceptions
                                Pattern p = Pattern.compile("^(mi:?\\d{4}\\|iid:?\\d{1})");
                                Matcher m = p.matcher(intrstStatus);

                                while (m.find()) {
                                    String mod = intrstStatus.split("\\|")[0].split(":")[1];
                                    String iid = intrstStatus.split("\\|")[1].split(":")[1];
                                    String key = mod + "..." + iid;

                                    // add to the set
                                    modViewSet.add(key);
                                }
                            }

                        } catch (Exception e) {
                            throw new JsonConvertException("Convert " + modView.toString() + " to json failed, " + e.getMessage(),
                                    e.getCause());
                        }

                    } else {

                        String key = this.getModuleStr(modViewStr,"|");

                        // add to the set
                        if (this.checkTagNotNull(modViewStr, "mi") && this.checkTagNotNull(modViewStr, "iid")) {
                            modViewSet.add(key);
                        }


                    }
                }


            }
        }


        // Load all module hscroll information to set "modHscrollSet"
        String moduleHscrollInStr ="";
        if(moduleHscrollIn != null){
             moduleHscrollInStr = moduleHscrollIn.toString();
        }
        List<String> hscrollLists = this.convertStrToList(moduleHscrollInStr);
        for (String item : hscrollLists) {
            if (!item.isEmpty() && !item.equals("")) {
                String key = this.getModuleStr(item,"|");
                    modHscrollSet.add(key);
            }
        }

        //Load all module information to initial map "modInfoClickImprnMap" .
        if (moduleInfos != null) {
            for (Text modInfo : moduleInfos) {
                if (StringUtils.isEmpty(modInfo.toString())) {
                    continue;
                }
                String[] modContents = modInfo.toString().split(":");

                String mod = modContents[0] + "...";
                String rgn = modContents[1];
                String rank = modContents[2];
                String curated = modContents[3];
                String iid = modContents[4];
                String key = mod + iid;

                ClickSurfaces imprn = new ClickSurfaces(rgn, rank, curated, iid);

                imprn.incrementSurfaces(1);
                //update the VIEWS to 1 if its
                if (modViewSet.contains(key)) {
                    imprn.setViews(1);
                }

                //update the HSCROLL TO 1 If its
                if(modHscrollSet.contains(key)){
                   imprn.setHscrolls(1);
                }

                modInfoClickImprnMap.put(key, imprn);

            }
        }


        //Load all the clicks into "modInfoClickImprnMap"
        if (moduleClicks != null) {
            for (Text module : moduleClicks) {
                String moduleStr = module.toString();
                if (StringUtils.isEmpty(moduleStr)) {
                    continue;
                }

                if (modInfoClickImprnMap.containsKey(moduleStr)) {
                    ClickSurfaces imprn = modInfoClickImprnMap.get(moduleStr);
                    imprn.incrementClicks(1);
                    modInfoClickImprnMap.put(moduleStr, imprn);
                } else {
                    String[] fields = moduleStr.split("\\.");
                    String instanceId;
                    if (fields.length < 4) {
                        //if there is no iid, set default iid to 1
                        instanceId = "1";
                    } else {
                        instanceId = fields[3];
                    }
                    String mod = fields[0] + "..." + instanceId;

                    if (modInfoClickImprnMap.containsKey(mod)) {

                        //Get the Region, Rank and Curated information from surface
                        String rgn = modInfoClickImprnMap.get(mod).getRegion();
                        String rank = modInfoClickImprnMap.get(mod).getRank();
                        String curated = modInfoClickImprnMap.get(mod).getIsCurated();
                        String iid = modInfoClickImprnMap.get(mod).getIid();

                        ClickSurfaces imprn = new ClickSurfaces(rgn, rank, curated, iid);
                        imprn.incrementClicks(1);
                        modInfoClickImprnMap.put(moduleStr, imprn);

                    } else {
                        ClickSurfaces imprn = new ClickSurfaces();
                        imprn.incrementClicks(1);
                        modInfoClickImprnMap.put(moduleStr, imprn);
                    }
                }
            }

        }

    }


    /**
     * Process with 2 input params
     **/

    public ArrayList<Text> evaluate(ArrayList<Text> moduleInfos, ArrayList<Text> moduleClicks) {

        /** Call the method that calculates the modInfoClickImprnMap map  */
        ArrayList<Text> dummy = new ArrayList<>();
        modifyClicks(moduleInfos, moduleClicks, dummy,dummy);

        //Writing the output from Map

        for (Map.Entry<String, ClickSurfaces> entry : modInfoClickImprnMap.entrySet()) {

            String module = entry.getKey();
            ClickSurfaces clickSurfaces = entry.getValue();


            //Module:Region:Rank:Curated:Clicks:Surfaces
            StringBuffer str = this.convertModuleStr(module,clickSurfaces);

            moduleClickImprns.add(new Text(str.toString()));

        }

        return moduleClickImprns;
    }


    /**
     * Process with 5 input params
     **/

    public ArrayList<Text> evaluate(Map<Text, Text> modulePlacements, ArrayList<Text> moduleInfos, ArrayList<Text> moduleClicks, ArrayList<Text> moduleViewsIn, ArrayList<Text> moduleHscrollIn) {

        modPlmtMap.clear();


        /**Create a map for the module placement only if key contains 5241  */
        if(modulePlacements != null) {
            modulePlacements.forEach((key, value) -> {

                if (key.toString().contains(MODULE_ID)) {

                    ModulePlmtImprns[] modulePlmts = JsonUtils.fromJson(value.toString(), ModulePlmtImprns[].class);

                    for (ModulePlmtImprns modulePlmt : modulePlmts) {

                        String moduleData = modulePlmt.getModuleData();

                        if (!moduleData.isEmpty()) {
                            try {
                                String intrstStatus = URLDecoder.decode(URLDecoder.decode(moduleData, ApplicationConstants.UTF8), ApplicationConstants.UTF8);
                                modPlmtMap.put(MODULE_ID, intrstStatus.split(":")[1]);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else {
                            modPlmtMap.put(MODULE_ID, "UNK");
                        }
                    }
                }

            });
        }


        /** Call the method that calculates the IMPRESSIIONS-VIEWS-CLICKS-HSCROLL */
        modifyClicks(moduleInfos, moduleClicks, moduleViewsIn, moduleHscrollIn);

        //Writing the output to final map "moduleClickImprns"
        for (Map.Entry<String, ClickSurfaces> entry : modInfoClickImprnMap.entrySet()) {

            String module = entry.getKey();
            ClickSurfaces clickSurfaces = entry.getValue();


            /** Check for the Interest module onboarding type and append its value**/
            String tempKey = module.split("\\.")[0];
            String intrstType = !(modPlmtMap.containsKey(tempKey)) ? "NA" : modPlmtMap.get(tempKey);

            //Module:Region:Rank:Curated:Clicks:Surfaces:Views:IntrstType:Hscrolls
            StringBuffer str = this.convertModuleStr(module,clickSurfaces);
            str.append(VALUE_DELIM)
                    .append(clickSurfaces.getViews()).append(VALUE_DELIM)
                    .append(intrstType).append(VALUE_DELIM)
                    .append(clickSurfaces.getHscrolls());

            moduleClickImprns.add(new Text(str.toString()));
        }
        return moduleClickImprns;
    }

}
