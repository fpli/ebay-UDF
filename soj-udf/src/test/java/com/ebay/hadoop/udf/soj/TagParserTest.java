package com.ebay.hadoop.udf.soj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TagParserTest {

    @Test
    public void testEvaluate_OneTag_OK() throws Exception {
        String statement = "create table P_CSI_TBS_T.weekly_visits_19 as ( \n" +
                "                    SELECT \n" +
                "                   e.GUID \n" +
                "                   ,CAST (e.site_id AS INTEGER) AS site_id \n" +
                "\t\t\t\t   ,s.COBRAND\n" +
                "                   ,e.SESSION_SKEY \n" +
                "                   ,e.SEQNUM \n" +
                "                   ,e.SESSION_START_DT    \n" +
                "                   ,s.start_timestamp\n" +
                "                   ,s.SESSION_CNTRY_ID\n" +
                "\t\t\t\t   \t,S.SESSION_TRAFFIC_SOURCE_ID\n" +
                "\t\t\t\t\t,S.CUST_TRAFFIC_SOURCE_LEVEL1\n" +
                "\t\t\t\t\t,S.CUST_TRAFFIC_SOURCE_LEVEL2\n" +
                "\t\t\t\t\t,S.DEVICE_TYPE_LEVEL1\n" +
                "\t\t\t\t\t,S.DEVICE_TYPE_LEVEL2\n" +
                "\t\t\t\t\t,S.EXPERIENCE_LEVEL1\n" +
                "\t\t\t\t\t,S.EXPERIENCE_LEVEL2\n" +
                "                   ,CASE WHEN sojlib.is_decimal(sojlib.soj_nvl(soj, 'itm'), 25, 0) = 1 THEN CAST(sojlib.soj_nvl(soj, 'itm') AS DECIMAL(25,0))\n" +
                "                   ELSE NULL     END AS item_id\n" +
                "                   FROM \n" +
                "                   P_SOJ_CL_V.CLAV_SESSION_EXT AS s \n" +
                "                   INNER JOIN ubi_v.ubi_event AS e ON \n" +
                "                   1=1 \n" +
                "                   AND s.guid = e.guid \n" +
                "                   AND s.session_skey = e.session_skey \n" +
                "                   AND s.session_start_dt = e.session_start_dt \n" +
                "                   );";

        TagParser parser = new TagParser();
        assertEquals(parser.evaluate(statement).size(), 1);
        assertTrue(parser.evaluate(statement).contains("itm"));
    }

    @Test
    public void testEvaluate_MultiTag_OK() throws Exception {
        String statement = "select \n" +
                "session_start_dt,\n" +
                "count(distinct seller_id) from (\n" +
                "select distinct \n" +
                "a.SESSION_START_DT session_start_dt,\n" +
                "a.vstr_usr_id seller_id\n" +
                "from ACCESS_VIEWS.DW_STORE_SOC_SHARE_EVENT a inner JOIN ACCESS_VIEWS.DW_USERS b \n" +
                "on a.VSTR_USR_ID=b.USER_ID \n" +
                "where b.USER_SITE_ID=15\n" +
                "and a.slr_id = a.vstr_usr_id\n" +
                "and a.SESSION_START_DT>='2022-04-22'\n" +
                "and a.vstr_usr_id not in (\n" +
                "select key from ACCESS_VIEWS.DW_STORE_SASCONFIG where BIZ_TYPE='BLACK_LIST'\n" +
                ")\n" +
                "union \n" +
                "select DISTINCT \n" +
                "e.SESSION_START_DT session_start_dt,\n" +
                "e.vistor_id seller_id\n" +
                "from ACCESS_VIEWS.DW_STORE_SHARE_EVENT e inner join ACCESS_VIEWS.dw_users b\n" +
                "on e.vistor_id=b.USER_ID\n" +
                "where e.SESSION_START_DT>='2022-04-22'\n" +
                "and b.USER_SITE_ID=15\n" +
                "and  (e.page_id in (2067411,2558860,2051244,2048329,2356359))\n" +
                "and (case when (e.page_id = 2067411 and sojlib.soj_nvl(soj,'spid')='6115')  then 1 \n" +
                "     when (e.page_id = 2067411 and sojlib.soj_nvl(soj,'spid')='2380784' and sojlib.soj_nvl(soj,'sid')='p2381057')  then 1 \n" +
                "     when (e.page_id in (2558860)) then  1 \n" +
                "     when (e.page_id = 2067411 and sojlib.soj_nvl(soj,'spid')='2269058')  then 1 \n" +
                "     when (e.page_id = 2067411 and sojlib.soj_nvl(soj,'spid')='2380784' and sojlib.soj_nvl(soj,'sid')='p2486981') then 1\n" +
                "     when (e.page_id = 2067411 and sojlib.soj_nvl(soj,'spid')='2349624') then 1 \n" +
                "     when (e.page_id = 2067411 and sojlib.soj_nvl(soj,'spid')='2047675')  then 1 \n" +
                "     when (e.page_id in (2051244)) then 1 \n" +
                "\t when (e.page_id = 2356359 and sojlib.soj_nvl(e.soj,'cp')=2349624 and sojlib.soj_nvl(e.soj,'moduledtl') like any (\"%mi%3A46890%7Cli%3A49286%\",\"%mi%3A46890%7Cli%3A6249%\",\"%mi%3A2548%7Cli%3A6249%\"))  then 1 \n" +
                "\t when (e.page_id in  (2048329)) then  1 \n" +
                "     when (e.page_id = 2067411 and sojlib.soj_nvl(soj,'spid')='2322090') then  1 \n" +
                "     when (e.page_id = 2067411 and sojlib.soj_nvl(soj,'spid')='2545226') then 1 \n" +
                "\t else 0 end ) = 1\n" +
                "\n" +
                "and e.vistor_id=e.SLR_ID \n" +
                "and e.vistor_id not in (\n" +
                "select key from ACCESS_VIEWS.DW_STORE_SASCONFIG where BIZ_TYPE='BLACK_LIST'\n" +
                ")\n" +
                ")group by 1 order by 1 asc";

        TagParser parser = new TagParser();
        assertEquals(parser.evaluate(statement).size(), 4);
        assertTrue(parser.evaluate(statement).contains("moduledtl"));
    }

    @Test
    public void testEvaluate_MultiTag_UpperCase_OK() throws Exception {
        String statement = "CREATE TEMP TABLE P_SD_T.AVE_VI_MODULES_CLICKS_VI_COUPON \n" +
                "USING DELTA \n" +
                "SELECT   GUID \n" +
                "      \t,SESSION_SKEY\n" +
                "\t  \t,SITE_ID\n" +
                "\t  \t,SESSION_START_DT\n" +
                "        ,ITEM_ID \n" +
                "\t\t,EXPRNC_NAME AS PLATFORM \n" +
                "\t\t,TRAFFIC_SRC_LVL4\t\tAS TRAFFIC_SOURCE\t\n" +
                "        ,SEQNUM\n" +
                "        ,PAGE_ID\n" +
                "\t\t,SRC_PAGE_ID\n" +
                "\t\t,USER_ID\n" +
                "\t\t\n" +
                "\t\n" +
                "\t    ,is_lndpg_flag\n" +
                "\t    ,sps_flag\n" +
                "\t    ,is_exitpg_flag\n" +
                "\t\t\n" +
                "        ,CASE WHEN sojlib.soj_nvl(SOJ, 'vicoupons') IS NOT NULL THEN 1 ELSE 0 END AS NATIVE_COUPON_SHOW\n" +
                "        ,CASE WHEN sojlib.soj_nvl(SOJ, 'vicoupondweb') IS NOT NULL THEN 1 ELSE 0 END AS DWEB_COUPON_SHOW\n" +
                "FROM ACCESS_VIEWS.VI_EVENT_FACT \n" +
                "WHERE SESSION_START_DT  BETWEEN  CURRENT_DATE-4 AND CURRENT_DATE-2\n" +
                "\n" +
                "\n" +
                "AND ITEM_ID>0\n" +
                "AND COBRAND IN  (0,6,7)";

        TagParser parser = new TagParser();
        assertEquals(parser.evaluate(statement).size(), 2);
        assertTrue(parser.evaluate(statement).contains("vicoupons"));
        assertTrue(parser.evaluate(statement).contains("vicoupondweb"));
    }
}