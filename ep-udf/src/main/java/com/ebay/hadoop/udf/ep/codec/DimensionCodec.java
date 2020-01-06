package com.ebay.hadoop.udf.ep.codec;

import com.ebay.hadoop.udf.ep.Constants;

/**
 * @author zilchen
 */
public class DimensionCodec {
    private static final int DIMENSION_ID_VALUE_BITS = 32;
    private static final long DIMENSION_ID_VALUE_MASK = (1L << DIMENSION_ID_VALUE_BITS) - 1;
    private static final int INHERENT_DIMENSION_BITS = 12;
    private static final long INHERENT_DIMENSION_MASK = (1L << INHERENT_DIMENSION_BITS) - 1;


    public static boolean isTreated(long inherentDims) {
        int treated = DimensionCodec.getInherentDimensionValue(inherentDims, 0);
        return treated == 1;
    }

    public static Long encodeDimensionIdValue(Integer dimensionId, Integer dimensionValue) {
        if (dimensionId == null) {
            return null;
        } else {
            if (dimensionValue == null) {
                dimensionValue = Constants.DIMENSION_NULL_VALUE;
            }
            return ((dimensionId.longValue()) << DIMENSION_ID_VALUE_BITS) | (dimensionValue.longValue() & DIMENSION_ID_VALUE_MASK);
        }
    }

    public static Integer getDimensionId(Long dimensionIdValue) {
        if (dimensionIdValue == null) {
            return null;
        } else {
            return (int) (dimensionIdValue.longValue() >> DIMENSION_ID_VALUE_BITS);
        }
    }

    public static Integer getDimensionValue(Long dimensionIdValue) {
        if (dimensionIdValue == null) {
            return null;
        } else {
            return dimensionIdValue.intValue();
        }
    }

    public static long encodeInherentDimension(Integer... dimensionValues) {
        long ret = 0;
        for (int i = 0; i < dimensionValues.length; i++) {
            Integer dimValue = dimensionValues[i];
            if (dimValue == null) {
                dimValue = Constants.DIMENSION_NULL_VALUE;
            }
            ret |= ((long) dimValue & INHERENT_DIMENSION_MASK) << (INHERENT_DIMENSION_BITS * i);
        }

        return ret;
    }

    public static long updateInherentDimension(long original, int index, Integer dimValue) {
        if (dimValue == null) {
            dimValue = Constants.DIMENSION_NULL_VALUE;
        }

        long removeMask = INHERENT_DIMENSION_MASK << (INHERENT_DIMENSION_BITS * index);
        long encodedDim = ((long) dimValue & INHERENT_DIMENSION_MASK) << (INHERENT_DIMENSION_BITS * index);
        return original & ~removeMask | encodedDim;
    }

    public static int getInherentDimensionValue(long encodedValue, int index) {
        int shift = 64 - (INHERENT_DIMENSION_BITS * (index + 1));
        return (int) (encodedValue << shift >> (64 - INHERENT_DIMENSION_BITS));
    }

    public static String prettify(long inherentDims) {
        return String.format("%s_%s_%s",
                DimensionCodec.getInherentDimensionValue(inherentDims, 0),
                DimensionCodec.getInherentDimensionValue(inherentDims, 1),
                DimensionCodec.getInherentDimensionValue(inherentDims, 2));
    }
}
