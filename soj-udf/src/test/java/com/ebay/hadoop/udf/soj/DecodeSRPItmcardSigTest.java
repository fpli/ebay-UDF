package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DecodeSRPItmcardSigTest {
    @Test
    public void test() {
        assertEquals(new IntWritable(0),
                new DecodeSRPItmcardSig().evaluate(new Text("AkAoAAIABA%3D%3D%2CJmIoACIAAA%3D%3D%2CBEIJACIBAA%3D%3D%2CJmIoACIAAA%3D%3D%2CJGIIACIBAA%3D%3D%2CNmJoACIVAA%3D%3D%2CMmAoAAIQBA%3D%3D%2CFEIIACIRAA%3D%3D%2CCCZiKQAqBQA%3D%2CFEIIACIRAA%3D%3D%2CCCZiKQAiBQA%3D%2CBEIIACIAAA%3D%3D%2CCCZiKQAiBQA%3D%2CNmAoAAIUBA%3D%3D%2CJGIIACIBAA%3D%3D%2CBEIIACIBAA%3D%3D%2CBEIIACIBAA%3D%3D%2CBEIIACIBAA%3D%3D%2CJGIJACIBAA%3D%3D%2CBEIJACIBAA%3D%3D%2CImRoAAIAAA%3D%3D%2CImIoAAIgAA%3D%3D%2CCCRiCQAiBQA%3D%2CBEIIACIBAA%3D%3D%2CJGIIACIBAA%3D%3D%2CJmJoAAIBAA%3D%3D%2CCCZiKQAiBQA%3D%2CImIoAAIAAA%3D%3D%2CBEIIACIBAA%3D%3D%2CFEIIACIRAA%3D%3D%2CImApAAEBAA%3D%3D%2CCDBiCAACFAA%3D%2CCCZiKAAiBQA%3D%2CFEQJACIRAA%3D%3D%2CNmAoAAIUBA%3D%3D%2CImIoAAIAAA%3D%3D%2CBEQIACIAAA%3D%3D%2CNmIoACIQAA%3D%3D%2CJGIIACIBAA%3D%3D%2CJGIIACIBAA%3D%3D%2CBEIJACIBAA%3D%3D%2CFEQJACIRAA%3D%3D%2CNmAoACIRBA%3D%3D%2CCCJgKACCCAA%3D%2CAgA0ZAgAIhEA%2CNGIIACIRAA%3D%3D%2CFEQIACIQAA%3D%3D%2CBEQIACIAAA%3D%3D"),
                        48,65));

        assertEquals(new IntWritable(1),
                new DecodeSRPItmcardSig().evaluate(new Text("NGQoACIQAA%3D%3D%2CCCJkKAACCAA%3D%2CImAoAIIAAA%3D%3D%2CAmAoAAIgAA%3D%3D%2CAkAoAAIgAA%3D%3D%2CAkAoAAIgAA%3D%3D%2CImQoAAIAAA%3D%3D%2CAkAoAAIAAA%3D%3D%2CImQpAAIgAA%3D%3D%2CFEIICCIRAA%3D%3D%2CImAoAAIAAA%3D%3D%2CNmAoACIwAA%3D%3D%2CJmQoAAIAAA%3D%3D%2CAgA2ZGgAIhEA%2CNmAoACIRAA%3D%3D%2CNmAoACIRAA%3D%3D%2CNmAoACIwAA%3D%3D%2CNmAoACIQAA%3D%3D%2CNmAoACIwAA%3D%3D%2CImAoAAIgAA%3D%3D%2CImAoAAIgAA%3D%3D%2CAkAoEAIAAA%3D%3D%2CJmAoAAIAAA%3D%3D%2CJmAoAAIAAA%3D%3D"),
                        14,65));

        assertEquals(new IntWritable(0),
                new DecodeSRPItmcardSig().evaluate(new Text("NGQoACIQAA%3D%3D%2CCCJkKAACCAA%3D%2CImAoAIIAAA%3D%3D%2CAmAoAAIgAA%3D%3D%2CAkAoAAIgAA%3D%3D%2CAkAoAAIgAA%3D%3D%2CImQoAAIAAA%3D%3D%2CAkAoAAIAAA%3D%3D%2CImQpAAIgAA%3D%3D%2CFEIICCIRAA%3D%3D%2CImAoAAI"),
                        14,65));

    }
}