package com.ebay.hadoop.udf.soj;

import com.ebay.hadoop.udf.tags.ETLUdf;
import com.ebay.sojourner.common.sojlib.SOJSampleHash;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.Text;

@ETLUdf(name = "soj_guidsampling")
public class GuidSampling extends UDF {
    public static final int MOD_VALUE = 1000;
    public static final int FETCH_BIT = 10;

    /**
     * Fetch the sample data by requiring how many percents, and the sampling is based on 
     * mod on guid.  Please consider adding partition to narrow the data volume.
     * 
     * @param guidText the guid text
     * @param pct how many percents required.
     * @return true, it belongs to the sample data.
     */
    public BooleanWritable evaluate(final Text guidText, int pct) {
        if (guidText == null) {
            return new BooleanWritable(false);
        }
        String guid = guidText.toString();
        int modValue = SOJSampleHash.sampleHash(guid, MOD_VALUE) / FETCH_BIT;
        if (modValue < pct) {
            return new BooleanWritable(true);
        } else {
            return new BooleanWritable(false);
        }
    }
}
