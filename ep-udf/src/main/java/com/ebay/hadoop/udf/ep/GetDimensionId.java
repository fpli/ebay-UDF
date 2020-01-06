package com.ebay.hadoop.udf.ep;

import com.ebay.hadoop.udf.ep.codec.DimensionCodec;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author zilchen
 */
public class GetDimensionId extends UDF {
    public long evaluate(long dimensionIdValue) {
        return DimensionCodec.getDimensionId(dimensionIdValue);
    }
}
