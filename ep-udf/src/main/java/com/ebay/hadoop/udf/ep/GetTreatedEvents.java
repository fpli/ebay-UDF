package com.ebay.hadoop.udf.ep;

import com.google.common.collect.Lists;
import it.uniroma3.mat.extendedset.intset.ConciseSet;
import it.uniroma3.mat.extendedset.intset.IntSet;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Collectors;

/*
 * This class is the UDF method to extract treated events from the encoded byte array.
 * As spark cannot parse java byte array, change the parameter to Object as a workaround.
 * Add ConciseSet and IntSet dependency to align with Touchstone repo.
 *
 */
public class GetTreatedEvents extends UDF {
    public String evaluate(Object obj) {
        if (obj == null) {
            return null;
        }
        ByteBuffer buf = ByteBuffer.wrap((byte[]) obj);
        int[] words = new int[buf.asIntBuffer().remaining()];
        buf.asIntBuffer().get(words);
        ConciseSet cs = new ConciseSet(words, false);
        IntSet.IntIterator iterator = cs.iterator();
        List<Integer> seqNums = Lists.newArrayList();
        while (iterator.hasNext()) {
            seqNums.add(iterator.next());
        }
        return seqNums.stream().map(String::valueOf).collect(Collectors.joining(","));
    }
}
