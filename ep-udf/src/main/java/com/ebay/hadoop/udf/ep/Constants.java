package com.ebay.hadoop.udf.ep;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * @author zilchen
 */
public class Constants {
    public static final long EMPTY_UID = -1L;

    public static final int EMPTY_CHANNEL = -1;
    public static final int INVALID_CHANNEL = 99;
    public static final int EXTERNAL_CHANNEL = -99;

    public static final int BU_TOUCHSTONE = -1;

    public static final int SAMPLE_COUNT_METRIC_ID = -1;
    public static final int TGI_SAMPLE_COUNT_METRIC_ID = -27;
    public static final int TREATED_SAMPLE_RATIO_METRIC_ID = -28;

    public static final int DIMENSION_NULL_VALUE = -777;
    public static final int DIMENSION_TOTAL_VALUE = -999;

    public static final double LAZY_METRIC_NO_VALUE_YET = -7777;

    public static final int INTERNAL_OBSERVATION_VIRTUAL_ID = -999;
    public static final int MULTI_SOURCE_OBSERVATION_VIRTUAL_ID = -9999;

    public static final int CORRUPTED_BIT_POS = -1;

    public static final long MINUTE_MS = 60 * 1000L;
    public static final long HOUR_MS = 60 * MINUTE_MS;
    public static final long DAY_MS = 24 * HOUR_MS;
    public static final long WEEK_MS = 7 * DAY_MS;
    public static final int SOJOURNER_LEG_MINUTES = 45;
    public static final int ROLLING_READY_MS = (24 * 60 + SOJOURNER_LEG_MINUTES) * 60 * 1000;
    public static final long DAILY_READY_MS = DAY_MS;

    public static final String TS_SUPPORT_MAIL = "DL-eBay-Touchstone-Support@ebay.com";
    public static final String TS_PAGERDUTY_MAIL = "touchstone-on-call-service-email.fe8uf6et@ebay-cpt.pagerduty.com";
    public static final String TS_EXTERNAL_ALERT_MAIL = "DL-eBay-Touchstone-Alert@ebay.com";
    public static final String TS_EP_TOOL_ALERT_MAIL = "DL-eBay-New-Eptools-Notification@ebay.com";

    public static final List<String> PROD_HOST_LIST
            = Collections.unmodifiableList(Lists.newArrayList("hms024", "hms025", "hms-asset00397756", "hms-asset00397759"));
    public static final List<String> QA_HOST_LIST
            = Collections.unmodifiableList(Lists.newArrayList("hms026", "hms-asset00397760"));

    public static final String TS_PROD_URL = "https://touchstone.corp.ebay.com";
    public static final String TS_QA_URL = "https://touchstone-qa.corp.ebay.com";

    public static final String ADMIN_ROLE_NAME = "ADMIN";
    public static final String POWER_ROLE_NAME = "POWER";
    public static final String CERTIFIED_ROLE_NAME = "CERTIFIED_USER";
    public static final String BU_ADMIN_ROLE_NAME = "BU_ADMIN";
    public static final String MAB_ROLE_NAME = "MAB";
    public static final String COMMA_SEPARATOR = ",";

    public static final String ELASTIC_ENDPOINT = "http://hms041.stratus.lvs.ebay.com:9200";
    public static final String DEFAULT_DB_NAME = "default";
    public static final String SMOKE_TEST_VISITOR_OBSERVATION = "touchstone_smoke_test_visitor_observation";
    public static final String SMOKE_TEST_VISITOR_QUALIFIED_OBSERVATION = "touchstone_smoke_test_visitor_qualified_observation";
    public static final String SMOKE_TEST_VISITOR_OBSERVATION_VIEW = "touchstone_smoke_test_visitor_observation_loaded";
    public static final String SMOKE_TEST_VISITOR_QUALIFIED_OBSERVATION_VIEW = "touchstone_smoke_test_visitor_observation_qualified";
    public static final String TS_JOB_CONF_SUFFIX = "Conf.xml";
    public static final String UC4_DW_PROD_1400_PROP = "uc4/dw_prod_1400.properties";
    public static final String SKIP_OPTIN_INFO_KEY = "Skip Optin";
    public static final String REPORT_REQUIRED_KEY = "Report Required";

    public static final String JENKINS_TRIGGER_PARAM = "tsbuild";

    private Constants() {
    }
}
