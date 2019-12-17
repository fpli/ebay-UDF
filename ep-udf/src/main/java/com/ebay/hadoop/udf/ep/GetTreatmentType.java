package com.ebay.hadoop.udf.ep;

import com.ebay.hadoop.udf.ep.codec.TrtmtCombinationCodec;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author zilchen
 */
public class GetTreatmentType extends UDF {
    public int evaluate(long combinationId) {
        return TrtmtCombinationCodec.getTreatType(combinationId).ordinal();
    }
}
