package com.ebay.hadoop.udf.ep.meta;

/**
 * The identifier of a treatment. Ideally treatment id is enough, but to be compatible with
 * experiments created with old ep tool, we still have version here, so the pipeline will be
 * able to get rid of the "double-counting" issue.
 *
 * @author zilchen
 */
public class TreatmentId {
    private long treatmentId;

    @Deprecated
    private int version;

    public TreatmentId(long treatmentId, int version) {
        this.treatmentId = treatmentId;
        this.version = version;
    }

    public long getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(long treatmentId) {
        this.treatmentId = treatmentId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
