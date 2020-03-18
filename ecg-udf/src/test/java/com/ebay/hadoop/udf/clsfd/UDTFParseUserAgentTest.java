package com.ebay.hadoop.udf.clsfd;


import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.io.File;

/**
 * Created by jianyahuang on 2019.02.12
 *
 * example from table: clsfd_working.CLSFD_ODIN_REPLY_EVENT_W_DK
 *
 * SELECT
 * clsfd_msg_uuid
 * ,request.userAgent useragent
 * , isMobile, isSmartPhone, isTablet, os, osversion, browserName, browserVersion, browserVendor
 * FROM vt_dk_reply_ins vt
 * lateral view ParseUserAgent2(vt.request.userAgent,'51Degrees-PremiumV3_2.dat','dk') ua AS
 *   isMobile, isSmartPhone, isTablet, os, osversion, browserName, browserVersion, browserVendor
 * limit 1
 * ;
 *
 * dba-606327da-e6ee-4c08-8877-7686f60bb7fc	Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.87 Safari/537.36
 * False	False	False	Windows	7	Unknown	Unknown	Unknown
 *
 */


public class UDTFParseUserAgentTest {

    @Test
    public void test() {
        String useragent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.87 Safari/537.36";

        File directory = new File("");
        String resourcesPath =directory.getAbsolutePath()+"\\src\\test\\resources\\";
        System.out.println(resourcesPath);

        String regexPropWhiteListPath = resourcesPath+"useragent_whitelist.regex";
        String regexPropPath = resourcesPath+"useragent.regex";
        String providerPath = resourcesPath+"51Degrees-PremiumV3_2.dat";
        String tenant="dk";

        Object[] inputObjs=new Object[4];
        inputObjs[0] = new Text(useragent);
        inputObjs[1] = new Text(regexPropWhiteListPath);
        inputObjs[2] = new Text(regexPropPath);
        inputObjs[3] = new Text(providerPath);

        Object[] outputObjs = new Object[8];
        outputObjs[0] = new Text("False");
        outputObjs[1] = new Text("False");
        outputObjs[2] = new Text("False");
        outputObjs[3] = new Text("Windows");
        outputObjs[4] = new Text("7");
        outputObjs[5] = new Text("Unknown");
        outputObjs[6] = new Text("Unknown");
        outputObjs[7] = new Text("Unknown");

        UDTFParseUserAgent parse=new UDTFParseUserAgent();
        try {
            ObjectInspector[] arg= new ObjectInspector[8];
            parse.initialize(arg);
        } catch (HiveException e) {
            e.printStackTrace();
        }

        // TODO: remove this comm if you want to run test ; And need to common clsaa forward
        // function of forward just for sql output that are multi-columns out ; to collect together
        //parse.process(inputObjs);
        //assertEquals(outputObjs,parse.forwardObjs);

    }

}