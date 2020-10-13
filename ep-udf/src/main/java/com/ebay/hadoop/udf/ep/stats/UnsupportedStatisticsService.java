package com.ebay.hadoop.udf.ep.stats;

import com.ebay.hadoop.udf.ep.stats.module.MetricStatsSummary;
import com.ebay.hadoop.udf.ep.stats.module.MetricSummaryGroup;

import java.util.List;

/**
 * For unsupported algo type.
 *
 * @author bingqxu
 */
public class UnsupportedStatisticsService implements TSStatisticsService<Object> {
    @Override
    public List<MetricStatsSummary> calcStats(MetricSummaryGroup summaryGroup, Object params) {
        throw new UnsupportedOperationException("Unsupported algo type");
    }

    @Override
    public MetricStatsSummary calcStats(MetricSummaryGroup summaryGroup, long treatmentId, Object params) {
        throw new UnsupportedOperationException("Unsupported algo type");
    }
}
