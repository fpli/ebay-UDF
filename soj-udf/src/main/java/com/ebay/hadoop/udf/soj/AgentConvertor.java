package com.ebay.hadoop.udf.soj;

import com.ebay.hadoop.udf.utils.TransformUtil;
import com.ebay.sojourner.common.sojlib.SOJParseClientInfo;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public final class AgentConvertor extends UDF {

    public static final String FILES_DELIM="\007";
    public Text evaluate(final Text userAgent) {
        if(userAgent==null||"".equals(userAgent.toString()))
            return new Text(0+FILES_DELIM+0);
        long[] long4AgentHash = TransformUtil.md522Long(TransformUtil.getMD5(userAgent.toString()));
        return new Text(long4AgentHash[0]+FILES_DELIM+long4AgentHash[1]);
    }
}
