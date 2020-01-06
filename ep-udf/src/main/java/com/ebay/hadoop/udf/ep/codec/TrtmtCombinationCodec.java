package com.ebay.hadoop.udf.ep.codec;

import com.ebay.hadoop.udf.ep.meta.TreatmentId;
import com.ebay.hadoop.udf.ep.meta.TreatmentType;
import com.google.common.base.Preconditions;

import java.util.StringTokenizer;

/**
 * Encode/decode experiment id, treatment id and treatment version into/from a 64 bit long.
 * The most significant bit will be used to indicate the treatment type.
 *
 * @author zilchen
 */
public class TrtmtCombinationCodec {
    private static final long EXPTID_LIMIT = 1 << 24;
    private static final long TRMTID_LIMIT = 1 << 26;
    private static final int VERSION_LIMIT = 1 << 11;

    private static final long TYPE_MASK = Long.MIN_VALUE | (1L << 62) | (1L << 61);
    private static final long REMOVE_TYPE_MASK = TYPE_MASK ^ (-1L);

    /**
     * Encode combinationId, treatmentId, treatmentVersion and treatment type.
     * @param exptId the experiment id, it takes the 12th to 35th bit from right.
     * @param treatmentId the treatment id. It takes the 36th to 61th bit from right. It is
     *                    deliberate that the treatment id takes the left most place of this
     *                    encoded long. We think treatmentId is more likely to exceed the 26
     *                    digit limit than other two fields, although very unlikely.
     * @param treatmentVersion the treatment version. It takes the right most 11 bit (0-10).
     * @param type the treatment type, it takes the left most 3 bit.
     * @return a encoded combinationId
     */
    public static long encode(long exptId, long treatmentId, int treatmentVersion, TreatmentType type) {
        Preconditions.checkArgument(exptId < EXPTID_LIMIT && exptId >= 0,
                "expt id is out of range " + exptId);
        Preconditions.checkArgument(treatmentId < TRMTID_LIMIT && treatmentId >= 0,
                "treatmentId is out of range " + treatmentId);
        Preconditions.checkArgument(treatmentVersion < VERSION_LIMIT && treatmentVersion >= 0,
                "treatmentVersion is out of range " + treatmentVersion);

        long withoutType = (treatmentId << 35) + (exptId << 11) + treatmentVersion;
        switch (type) {
            case Treatment:
                return withoutType;
            case Control:
                return withoutType | Long.MIN_VALUE;
            case CombinedControl:
                return withoutType | Long.MIN_VALUE | (1L << 62);
            default:
                throw new RuntimeException("Unsupported treatment type " + type.name());
        }
    }

    /**
     * Convert combination id to external treatment id.
     * @param combinationId the combination id.
     * @return the external treatment identifier.
     */
    public static String extern(long combinationId) {
        return extern(getTreatmentId(combinationId), getVersionId(combinationId), getTreatType(combinationId));
    }

    /**
     * Encode a external treatment identifier. It is different from combination id as it doesn't
     * contain experiment id. The experiment id was encoded in combination id to ease inside the
     * touchstone project, but it is actually redundant as an identifier of treatment. So when we
     * send data to external, we use another string identifier. This is to shorten the length of
     * the identifier we communicate between frontend and touchstone backend.
     *
     * @param treatmentId the treatment id.
     * @param treatmentVersion the treatment version.
     * @param treatmentType the treatment type.
     * @return the external treatment string identifier.
     */
    public static String extern(long treatmentId, int treatmentVersion, TreatmentType treatmentType) {
        return Long.toHexString(treatmentId) + '_' + Integer.toHexString(treatmentVersion)
                + '_' + treatmentType.ordinal();
    }

    /**
     * Get combination id from an external treatment id. The external id doesn't have the experiment id,
     * therefore it needs to be passed in explicitly.
     * @param exptId the experiment id.
     * @param externTreatmentId the external treatment id.
     * @return the combination id.
     */
    public static long intern(long exptId, String externTreatmentId) {
        StringTokenizer tokenizer = new StringTokenizer(externTreatmentId, "_");
        return encode(exptId, Long.valueOf(tokenizer.nextToken(), 16),
                Integer.valueOf(tokenizer.nextToken(), 16),
                TreatmentType.values()[Integer.valueOf(tokenizer.nextToken())]);
    }

    /**
     * Get the experiment id from the combination id.
     *
     * @param combinationId the combination id.
     * @return the experiment id.
     */
    public static long getExptId(long combinationId) {
        combinationId = combinationId & REMOVE_TYPE_MASK;
        return (combinationId >> 11) & ((1L << 24) - 1);
    }

    /**
     * Get the treatment id from the combination id.
     * @param combinationId the combination id.
     * @return the treatment id.
     */
    public static long getTreatmentId(long combinationId) {
        combinationId = combinationId & REMOVE_TYPE_MASK;
        return combinationId >> 35;
    }

    /**
     * Get the treatment version from the combination id.
     * @param combinationId the combination id.
     * @return the treatment version.
     */
    public static int getVersionId(long combinationId) {
        return (int)(combinationId & ((1L << 11) - 1));
    }

    /**
     * Get the treatment type, i.e. test or control of a treatment.
     * @param combinationId the combination id.
     * @return {@link TreatmentType} of the encoded treatment.
     */
    public static TreatmentType getTreatType(long combinationId) {
        if (combinationId >= 0) {
            return TreatmentType.Treatment;
        } else {
            return (combinationId & (1L << 62)) != 0 ? TreatmentType.CombinedControl : TreatmentType.Control;
        }
    }

    public static String prettify(long combinationId) {
        return TrtmtCombinationCodec.getExptId(combinationId) + "-" + TrtmtCombinationCodec.getTreatmentId(combinationId)
                + "-" + TrtmtCombinationCodec.getVersionId(combinationId) + "-"
                + (TrtmtCombinationCodec.getTreatType(combinationId) == TreatmentType.Treatment ? "T" : "C");
    }

    public static TreatmentId convert2TreatmentId(long combinationId) {
        return new TreatmentId(getTreatmentId(combinationId), getVersionId(combinationId));
    }
}
