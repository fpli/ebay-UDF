package com.ebay.hadoop.udf.ep;

import com.ebay.hadoop.udf.ep.codec.TreatmentInfoCodec;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author zilchen
 */
public class GetTreatedType extends UDF {
    public int evaluate(long treatmentInfo) {
        return TreatmentInfoCodec.getTreatedType(treatmentInfo).ordinal();
    }
}
