package com.ebay.hadoop.udf.ep;

import com.ebay.hadoop.udf.ep.codec.TrtmtCombinationCodec;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author zilchen
 */
public class GetTreatmentId extends UDF {
    public long evaluate(long combinationId) {
        return TrtmtCombinationCodec.getTreatmentId(combinationId);
    }
}
