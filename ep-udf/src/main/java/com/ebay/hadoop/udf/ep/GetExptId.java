package com.ebay.hadoop.udf.ep;

import com.ebay.hadoop.udf.ep.codec.TrtmtCombinationCodec;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author zilchen
 */
public class GetExptId extends UDF {
    public long evaluate(long combinationId) {
        return TrtmtCombinationCodec.getExptId(combinationId);
    }
}
