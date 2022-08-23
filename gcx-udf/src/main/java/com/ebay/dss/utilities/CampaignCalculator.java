package com.ebay.dss.utilities;

import org.apache.commons.codec.digest.MurmurHash3;

public class CampaignCalculator {
    public static Integer evaluate(String customerId, String startdate, Double split) {
        String seed = customerId + startdate;
        byte[] bytes = seed.getBytes();
        int hash = MurmurHash3.hash32x86(bytes);
        if (Math.abs(hash) < split * Integer.MAX_VALUE) {
            return 1;
        } else {
            return 0;
        }
    }

    public static Integer evaluate(Long customerId, String startdate, Double split)  {
        return evaluate(customerId.toString(), startdate, split);
    }

}
