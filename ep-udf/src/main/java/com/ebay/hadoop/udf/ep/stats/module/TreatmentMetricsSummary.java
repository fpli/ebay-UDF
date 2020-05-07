package com.ebay.hadoop.udf.ep.stats.module;


import com.ebay.hadoop.udf.ep.meta.TreatmentType;

import java.util.Collection;

/**
 * Metric history holder for a single treatment.
 *
 * @author bingqxu
 */
public interface TreatmentMetricsSummary {
    long getTreatmentId();

    double getTrafficPct();

    TreatmentType getTreatmentType();

    /**
     * Data of different time ranges may need a merge.
     * @param that the summary that to be merged.
     */
    void merge(TreatmentMetricsSummary that);

    Collection<MetricSummaryHistory> getMetricSummaries();

    MetricSummaryHistory getMetricSummary(int metricId);
}
