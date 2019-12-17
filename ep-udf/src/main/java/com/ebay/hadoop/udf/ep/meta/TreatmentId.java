package com.ebay.hadoop.udf.ep.meta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The identifier of a treatment. Ideally treatment id is enough, but to be compatible with
 * experiments created with old ep tool, we still have version here, so the pipeline will be
 * able to get rid of the "double-counting" issue.
 *
 * @author zilchen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentId {
    private long treatmentId;

    @Deprecated
    private int version;
}
