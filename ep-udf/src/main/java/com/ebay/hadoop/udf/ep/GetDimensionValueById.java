package com.ebay.hadoop.udf.ep;

import com.ebay.hadoop.udf.ep.codec.DimensionCodec;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.List;
import java.util.Optional;

/**
 * @author zilchen
 */
public class GetDimensionValueById extends UDF {
    public int evaluate(List<Long> dimensions, int dimensionId) {
        Optional<Long> ret = dimensions.stream().filter(dim -> DimensionCodec.getDimensionId(dim) == dimensionId).findAny();
        if (ret.isPresent()) {
            return DimensionCodec.getDimensionValue(ret.get());
        } else {
            return Constants.DIMENSION_NULL_VALUE;
        }
    }
}
