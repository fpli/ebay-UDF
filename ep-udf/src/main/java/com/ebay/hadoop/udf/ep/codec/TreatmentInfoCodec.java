package com.ebay.hadoop.udf.ep.codec;

import com.ebay.hadoop.udf.ep.meta.QualificationType;
import com.ebay.hadoop.udf.ep.meta.TreatedType;

/**
 * Encode treatment level info to save space.
 * @author zilchen
 */
public class TreatmentInfoCodec {
    private static final int TREATED_BITS = 4;
    private static final int XT_POS = 32;
    private static final int TRC_POS = 33;
    private static final long TREATED_MASK = ((1L << TREATED_BITS) - 1) << XT_POS;

    private static final int QT_BITS = 4;
    private static final int QT_MASK = (1 << QT_BITS) - 1;
    private static final int NQT_POS = 0;
    private static final int MOD_POS = 1;


    public static long initial() {
        return 0L;
    }

    public static long encode(TreatedType treatedType, QualificationType qualificationType) {
        long treatmentInfo = initial();
        return encode(treatmentInfo, treatedType) | encode(treatmentInfo, qualificationType);
    }

    public static long encode(long treatmentInfo, TreatedType treatedType) {
        switch (treatedType) {
            case XT:
                treatmentInfo = treatmentInfo | (1L << XT_POS);
                break;
            case TRC:
                treatmentInfo = treatmentInfo | (1L << TRC_POS);
                break;
            case UnTreated:
                treatmentInfo = treatmentInfo & (~TREATED_MASK);
                break;
            default:
                throw new RuntimeException("unsupported treated type" + treatedType.name());
        }
        return treatmentInfo;
    }

    public static long encode(long treatmentInfo, QualificationType qualificationType) {
        switch (qualificationType) {
            case NQT:
                treatmentInfo = treatmentInfo | (1L);
                break;
            case MOD:
                treatmentInfo = treatmentInfo | (1L << MOD_POS);
                break;
            case NQT_AND_MOD:
                treatmentInfo = treatmentInfo | (1L) | (1L << MOD_POS);
                break;
            default:
                throw new RuntimeException("unsupported qualification type" + qualificationType.name());
        }
        return treatmentInfo;
    }

    public static long merge(long first,long second) {
        return first | second;
    }

    public static boolean isTreated(long treatmentInfo) {
        return (treatmentInfo & TREATED_MASK) != 0;
    }

    public static boolean isNQT(long treatmentInfo) {
        return (treatmentInfo & 1L) != 0;
    }

    public static boolean isMod(long treatmentInfo) {
        return (treatmentInfo & (1L << MOD_POS)) != 0;
    }

    public static QualificationType getQualificationType(long treatmentInfo) {
        boolean isNqt = isNQT(treatmentInfo);
        boolean isMod = isMod(treatmentInfo);
        if (isNqt && isMod) {
            return QualificationType.NQT_AND_MOD;
        } else if (isNqt) {
            return QualificationType.NQT;
        } else if (isMod) {
            return QualificationType.MOD;
        } else {
            return QualificationType.NOT_APPLICABLE;
        }
    }

    public static TreatedType getTreatedType(long treatmentInfo) {
        long isXtTreated = treatmentInfo & (1L << XT_POS);
        long isTRCTreated = treatmentInfo & (1L << TRC_POS);
        if (isXtTreated != 0) {
            return TreatedType.XT;
        }
        if (isTRCTreated != 0) {
            return TreatedType.TRC;
        }
        return TreatedType.UnTreated;
    }
}
