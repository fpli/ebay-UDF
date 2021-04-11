package com.ebay.hadoop.udf.ep.meta;

import com.google.common.base.Preconditions;
import it.uniroma3.mat.extendedset.intset.ConciseSet;

import java.nio.ByteBuffer;

/*
 * This class is used to mock treated events in UT.
 * Add ConciseSet dependency to align with Touchstone repo.
 *
 */
public class TreatedDecision {
    private TreatedType firstTreatedType;

    private final ConciseSet cs;

    public TreatedDecision() {
        this.firstTreatedType = TreatedType.UnTreated;
        this.cs = new ConciseSet();
    }

    public void treated(int seqnum, TreatedType treatedType) {
        if (firstTreatedType == TreatedType.UnTreated) {
            firstTreatedType = treatedType;
        }
        cs.add(seqnum);
    }

    TreatedType getFirstTreatedType() {
        return firstTreatedType;
    }

    public ByteBuffer toByteBuffer() {
        if (isTreated()) {
            Preconditions.checkState(!cs.isEmpty());
            return cs.toByteBuffer();
        }
        return null;
    }

    public boolean isTreated() {
        return firstTreatedType == TreatedType.XT || firstTreatedType == TreatedType.TRC;
    }
}
