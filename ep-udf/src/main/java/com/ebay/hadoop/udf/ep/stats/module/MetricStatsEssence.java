package com.ebay.hadoop.udf.ep.stats.module;

import com.ebay.hadoop.udf.ep.stats.util.TSStatsUtil;
import com.google.common.base.Preconditions;

/**
 * A summary of metrics for statistical calculation.
 *
 * @author bingqxu
 */
public class MetricStatsEssence implements MetricSummary {
    private final int metricId;

    private double sum;

    private double sumOfSquare;

    private double sumOfCube;

    private double sumOfTheFourthPower;

    private long metricCount;

    /**
     * It may be the same with metric count for some per sample ratio metrics.
     */
    private long sampleCount;

    private final long timestamp;

    public MetricStatsEssence(int metricId, double sum, double sumOfSquare, double sumOfCube,
                              double sumOfTheFourthPower, long metricCount, long sampleCount, long timestamp) {
        this.metricId = metricId;
        this.sum = sum;
        this.sumOfSquare = sumOfSquare;
        this.sumOfCube = sumOfCube;
        this.sumOfTheFourthPower = sumOfTheFourthPower;
        this.metricCount = metricCount;
        this.sampleCount = sampleCount;
        this.timestamp = timestamp;
    }

    public int getMetricId() {
        return metricId;
    }

    @Override
    public double getMean() {
        return TSStatsUtil.getMean(sum, metricCount);
    }

    @Override
    public double getStddev() {
        double variance = TSStatsUtil.getVariance(sum, sumOfSquare, metricCount);
        return Math.sqrt(variance);
    }

    @Override
    public double getExtrapolatedMean() {
        return TSStatsUtil.getMean(sum, sampleCount);
    }

    @Override
    public double getExtrapolatedStddev() {
        return TSStatsUtil.calculateExtrapolatedStddev(sumOfSquare, getExtrapolatedMean(), sampleCount);
    }

    // Must check NaN or Infinite while call this function
    @Override
    public double getExtrapolatedCV() {
        return getExtrapolatedStddev() / getExtrapolatedMean();
    }

    // Must check NaN while call this function
    @Override
    public double getExtrapolatedSkewness() {
        return TSStatsUtil.calculateExtrapolatedSkewness(sumOfSquare, sumOfCube, getExtrapolatedMean(), sampleCount);
    }

    // Must check NaN while call this function
    @Override
    public double getExtrapolatedKurtosis() {
        return TSStatsUtil.calculateExtrapolatedKurtosis(sumOfSquare, sumOfCube, sumOfTheFourthPower,
                getExtrapolatedMean(), sampleCount);
    }

    @Override
    public void combine(MetricSummary summary) {
        Preconditions.checkArgument(this.metricId == summary.getMetricId());
        Preconditions.checkArgument(this.timestamp == summary.getTimestamp());
        Preconditions.checkArgument(summary instanceof MetricStatsEssence);
        MetricStatsEssence essential = (MetricStatsEssence) summary;
        this.sum += essential.getSum();
        this.sumOfSquare += essential.getSumOfSquare();
        this.sumOfCube += essential.getSumOfCube();
        this.sumOfTheFourthPower += essential.getSumOfTheFourthPower();
        this.metricCount += essential.getMetricCount();
        this.sampleCount += essential.getSampleCount();
    }

    @Override
    public MetricSummary copy() {
        return new MetricStatsEssence(this.metricId, this.sum, this.sumOfSquare, this.sumOfCube, this.sumOfTheFourthPower,
                this.metricCount, this.sampleCount, this.timestamp);
    }

    public double getSum() {
        return sum;
    }

    public double getSumOfSquare() {
        return sumOfSquare;
    }

    public double getSumOfCube() {
        return sumOfCube;
    }

    public double getSumOfTheFourthPower() {
        return sumOfTheFourthPower;
    }

    public long getMetricCount() {
        return metricCount;
    }

    public long getSampleCount() {
        return sampleCount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setSampleCount(long sampleCount) {
        this.sampleCount = sampleCount;
    }

    @Override
    public String toString() {
        return "MetricStatsEssential{" + "metricId=" + metricId + ", sum=" + sum + ", sumOfSquare=" + sumOfSquare
                + ", sumOfCube=" + sumOfCube + ", sumOfTheFourthPower=" + sumOfTheFourthPower
            + ", metricCount=" + metricCount + ", sampleCount=" + sampleCount + ", timestamp=" + timestamp + '}';
    }
}
