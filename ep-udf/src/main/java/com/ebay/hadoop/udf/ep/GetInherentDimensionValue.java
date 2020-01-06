package com.ebay.hadoop.udf.ep;

import com.ebay.hadoop.udf.ep.codec.DimensionCodec;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author zilchen
 */
public class GetInherentDimensionValue extends UDF {
    public long evaluate(long encodeValue, int index) {
        return DimensionCodec.getInherentDimensionValue(encodeValue, index);
    }
}
