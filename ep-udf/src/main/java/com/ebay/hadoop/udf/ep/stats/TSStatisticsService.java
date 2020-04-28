package com.ebay.hadoop.udf.ep.stats;


import com.ebay.hadoop.udf.ep.stats.module.MetricStatsSummary;
import com.ebay.hadoop.udf.ep.stats.module.MetricSummaryGroup;

import java.util.List;

/**
 * Provides statistics to paired summary. A paired summary is a pair of treatment and control,
 * this service provides statistics based on the summary of the treatment and the control.
 *
 * @author bingqxu
 */
public interface TSStatisticsService<T> {
    List<MetricStatsSummary> calcStats(MetricSummaryGroup summaryGroup, T params);

    MetricStatsSummary calcStats(MetricSummaryGroup summaryGroup, long treatmentId, T params);
}
