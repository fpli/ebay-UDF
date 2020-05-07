package com.ebay.hadoop.udf.ep.stats.module;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * A group of metric summaries that share the same treatment id and timestamp. It contains
 * a list of metric summaries from different treatment ids.
 *
 * @author bingqxu
 */
public class MetricSummaryGroup {
    private final long inspectId;

    private final int metricId;

    private final long timestamp;

    private List<PITTreatmentMetricSummary> summaries;

    public MetricSummaryGroup(long inspectId, int metricId, long timestamp) {
        this(inspectId, metricId, timestamp, Lists.newArrayList());
    }

    @VisibleForTesting
    public MetricSummaryGroup(long inspectId, int metricId, long timestamp, List<PITTreatmentMetricSummary> summaries) {
        this.inspectId = inspectId;
        this.metricId = metricId;
        this.timestamp = timestamp;
        this.summaries = summaries;
    }

    public long getInspectId() {
        return inspectId;
    }

    public int getMetricId() {
        return metricId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public List<PITTreatmentMetricSummary> getSummaries() {
        return summaries;
    }

    public void attach(PITTreatmentMetricSummary summary) {
        summaries.add(summary);
    }
}
