package com.ebay.hadoop.udf.ep.stats.module;

import org.apache.commons.lang.NotImplementedException;

/**
 * Provides summarized number of a metric for a particular treatment.
 *
 * @author bingqxu
 */
public interface MetricSummary {
    int getMetricId();

    double getSum();

    double getMean();

    double getStddev();

    default double getCV() {
        return getStddev() / getMean();
    }

    long getTimestamp();

    long getMetricCount();

    default long getSampleCount() {
        return getMetricCount();
    }

    default double getExtrapolatedMean() {
        return getMean();
    }

    default double getExtrapolatedStddev() {
        return getStddev();
    }

    default double getExtrapolatedCV() {
        return getCV();
    }

    default double getExtrapolatedSkewness() {
        throw new NotImplementedException("MetricSummary.getSkewness() not implemented!");
    }

    default double getExtrapolatedKurtosis() {
        throw new NotImplementedException("MetricSummary.getKurtosis() not implemented!");
    }

    /**
     * Used when multiple treatments' data needs to be combined as one. A typical
     * usage is the combined control. Therefore, the timestamp must be the same and
     * the num units will not be changed after the combine.
     *
     * @param summary the summary to be combined.
     */
    void combine(MetricSummary summary);

    MetricSummary copy();
}
