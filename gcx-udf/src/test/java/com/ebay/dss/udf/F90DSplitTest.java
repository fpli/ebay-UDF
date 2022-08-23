
package com.ebay.dss.udf;

import com.ebay.dss.utilities.CampaignCalculator;
import org.junit.Test;

public class F90DSplitTest {

    @Test
    public void case1() {

        F90DSplit split = new F90DSplit();
        assert (split.evaluate("324782634", "20220-06-23", 0.8) == 0);
		assert (CampaignCalculator.evaluate("324782634", "20220-06-23", 0.8) == 0);
		assert (CampaignCalculator.evaluate(324782634L, "20220-06-23", 0.8) == 0);
		
        assert (split.evaluate("324782634", "20220-06-24", 0.8) == 1);
        assert (CampaignCalculator.evaluate("324782634", "20220-06-24", 0.8) == 1);
        assert (CampaignCalculator.evaluate(324782634L, "20220-06-24", 0.8) == 1);
    }

}
