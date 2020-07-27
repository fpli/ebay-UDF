package com.ebay.dss.zeta.hive

import org.apache.commons.lang.StringUtils
import org.apache.hadoop.hive.ql.exec.UDF
import com.ebay.hadoop.scalaplatform.helpers.SojHelpers

class ZheTestUDF extends UDF {

    def evaluate(s:java.lang.String, siteId:java.lang.Long):java.lang.String =
    {
       SojHelpers.stringNormalizedSIBEStyle(Option(s), siteId).get
    }
}
