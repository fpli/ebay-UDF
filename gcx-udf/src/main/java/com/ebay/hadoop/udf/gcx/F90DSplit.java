package com.ebay.hadoop.udf.gcx;

import com.ebay.hadoop.udf.gcx.utilities.CampaignCalculator;
import org.apache.hadoop.hive.ql.exec.UDF;

public class F90DSplit extends UDF {
    public Integer evaluate(String customerid, String startdate, Double split)  {
        return CampaignCalculator.evaluate(customerid,startdate,split);
    }
}
