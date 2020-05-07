package com.ebay.hadoop.udf.ep.stats;



import com.ebay.hadoop.udf.ep.stats.module.MetricStatsSummary;
import com.ebay.hadoop.udf.ep.stats.module.MetricSummaryGroup;
import com.ebay.hadoop.udf.ep.stats.module.StatsAlgorithmType;

import java.util.List;

/**
 * Readout methodology.
 *
 * @author bingqxu
 */
public class ReadoutConfig {
    private final StatsAlgorithmType methodology;

    private final Object param;

    public ReadoutConfig(StatsAlgorithmType methodology, Object param) {
        this.methodology = methodology;
        this.param = param;
    }

    public StatsAlgorithmType getMethodology() {
        return methodology;
    }

    @SuppressWarnings("unchecked")
    public <T> T getParam() {
        return (T) param;
    }

    public List<MetricStatsSummary> calcStats(MetricSummaryGroup pairedSummary) {
        return methodology.getStatisticsService().calcStats(pairedSummary, param);
    }

    /**
     * Only calculate statistics for a particular treatment id.
     * @return
     */
    public MetricStatsSummary calcStats(MetricSummaryGroup pairedSummary, long treatmentId) {
        return methodology.getStatisticsService().calcStats(pairedSummary, treatmentId, param);
    }
}
