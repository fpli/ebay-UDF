package org.monstor.entitymapping.spark.udf;

import org.junit.Test;

public class UDAFTest {
    @Test
    public void testSchema(){
        DocumentGroupFragmentUDAF agg = new DocumentGroupFragmentUDAF();
        agg.bufferSchema().printTreeString();
        agg.inputSchema().printTreeString();
    }
}
