package com.ebay.hadoop.udf.clsfd;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jianyahuang on 2019.02.24
 *
 * select
 * ad.id as src_ad_id,
 * ad.time as src_ad_time,
 * fromunixtime(ad.time, 'Europe/London') as src_cre_dttm
 * from dw_clsfd.gtuk_adverts_by_device ad
 * limit 3
 * ;
 * src_ad_id	src_ad_time	src_cre_dttm
 * 1260184659	1502233219853	2017-08-09 00:00:19
 * 1260184675	1502233231243	2017-08-09 00:00:31
 * 1260184703	1502233250710	2017-08-09 00:00:50
 *
 */


public class FromUnixtimeTest {

    @Test
    public void test() {
        FromUnixtime fu = new FromUnixtime();

        String resultStr = fu.evaluate("1427812346273", "GMT+8");
        assertEquals(resultStr ,"2015-03-31 22:32:26");

        resultStr = fu.evaluate("1502233231243", "Europe/London");
        assertEquals(resultStr ,"2017-08-09 00:00:31");


    }

}
