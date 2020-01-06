package com.ebay.hadoop.udf.ep.meta;

/**
 * @author zilchen
 */
public enum QualificationType {
    NOT_APPLICABLE(0),
    NQT(1),
    MOD(2),
    NQT_AND_MOD(3);

    private int value;

    QualificationType(int value) {
        this.value = value;
    }
}
