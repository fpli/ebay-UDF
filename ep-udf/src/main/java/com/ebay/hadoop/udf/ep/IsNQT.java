package com.ebay.hadoop.udf.ep;

import com.ebay.hadoop.udf.ep.codec.TreatmentInfoCodec;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author zilchen
 */
public class IsNQT extends UDF {
    public boolean evaluate(long treatmentInfo) {
        return TreatmentInfoCodec.isNQT(treatmentInfo);
    }
}
