package com.ebay.hadoop.udf.ep.stats.module;


import com.ebay.hadoop.udf.ep.meta.TreatmentType;

/**
 * Point-of-time treatment metric summary. It is the summary of a particular metric of
 * a treatment at a given timestamp. It also includes some treatment level info at
 * that timestamp.
 *
 * @author bingqxu
 */
public class PITTreatmentMetricSummary {
    private long treatmentId;

    private double trafficPct;

    private TreatmentType treatmentType;

    private long timestamp;

    private int metricId;

    private MetricSummary metricSummary;

    public PITTreatmentMetricSummary(long treatmentId, double trafficPct, TreatmentType treatmentType,
                                     long timestamp, int metricId, MetricSummary metricSummary) {
        this.treatmentId = treatmentId;
        this.trafficPct = trafficPct;
        this.treatmentType = treatmentType;
        this.timestamp = timestamp;
        this.metricId = metricId;
        this.metricSummary = metricSummary;
    }

    public long getTreatmentId() {
        return treatmentId;
    }

    public double getTrafficPct() {
        return trafficPct;
    }

    public TreatmentType getTreatmentType() {
        return treatmentType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getMetricId() {
        return metricId;
    }

    public MetricSummary getMetricSummary() {
        return metricSummary;
    }
}
