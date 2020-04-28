package com.ebay.hadoop.udf.ep.stats.module;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Metrics summary history of a single metric.
 *
 * @author bingqxu
 */
public class MetricSummaryHistory {
    private int metricId;

    private List<MetricSummary> summaries;

    public MetricSummaryHistory(int metricId) {
        this(metricId, Lists.newArrayList());
    }

    public MetricSummaryHistory(int metricId, List<MetricSummary> summaries) {
        this.metricId = metricId;
        this.summaries = summaries;
    }

    public void setMetricId(int metricId) {
        this.metricId = metricId;
    }

    public int getMetricId() {
        return metricId;
    }

    public List<MetricSummary> getSummaries() {
        return summaries;
    }

    public MetricSummary getLatest() {
        return summaries.isEmpty() ? null : summaries.get(summaries.size() - 1);
    }

    void attach(MetricSummary summary) {
        Preconditions.checkArgument(metricId == summary.getMetricId());
        long currentLatest = summaries.isEmpty() ? 0L : summaries.get(summaries.size() - 1).getTimestamp();
        Preconditions.checkArgument(summary.getTimestamp() > currentLatest);
        summaries.add(summary);
    }

    void attachSampleCount(long sampleCount) {
        Preconditions.checkArgument(!summaries.isEmpty());
        MetricSummary summary = summaries.get(summaries.size() - 1);
        Preconditions.checkArgument(summary instanceof MetricStatsEssence);
        MetricStatsEssence essence = (MetricStatsEssence) summary;
        essence.setSampleCount(sampleCount);
    }

    public void merge(MetricSummaryHistory that) {
        Preconditions.checkArgument(that.metricId == metricId);
        //merge two sorted list into one.
        List<MetricSummary> newList = Lists.newArrayList();
        int i = 0;
        int j = 0;
        while (i < summaries.size() && j < that.summaries.size()) {
            MetricSummary summary1 = summaries.get(i);
            MetricSummary summary2 = that.summaries.get(j);
            Preconditions.checkState(summary1.getTimestamp() != summary2.getTimestamp());
            if (summary1.getTimestamp() < summary2.getTimestamp()) {
                newList.add(summary1);
                ++i;
            } else {
                newList.add(summary2);
                ++j;
            }
        }

        while (i < summaries.size()) {
            newList.add(summaries.get(i));
            ++i;
        }

        while (j < that.summaries.size()) {
            newList.add(that.summaries.get(j));
            ++j;
        }

        this.summaries = newList;
    }
}
