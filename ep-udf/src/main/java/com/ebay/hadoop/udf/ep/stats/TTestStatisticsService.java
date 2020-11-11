package com.ebay.hadoop.udf.ep.stats;

import com.ebay.hadoop.udf.ep.Constants;
import com.ebay.hadoop.udf.ep.meta.TreatmentType;
import com.ebay.hadoop.udf.ep.stats.module.MetricStatsSummary;
import com.ebay.hadoop.udf.ep.stats.module.MetricSummary;
import com.ebay.hadoop.udf.ep.stats.module.MetricSummaryGroup;
import com.ebay.hadoop.udf.ep.stats.module.PITTreatmentMetricSummary;
import com.ebay.hadoop.udf.ep.stats.module.StatsAlgorithmType;
import com.ebay.hadoop.udf.ep.stats.util.MathUtil;
import com.ebay.hadoop.udf.ep.stats.util.TSStatsUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.List;

/**
 *
 * @author bingqxu
 */
public class TTestStatisticsService implements TSStatisticsService<Double> {
    private final ThreadLocal<TSStatsUtil> tlTsStatsUtil;
    private final ThreadLocal<NormalDistribution> tlNnormalDist;

    private final double zalpha4ImpliedMDE;
    private final double zbeta4ImpliedMDE;

    private final static double IMPLIED_MDE_ALPHA = 0.1d;
    private final static double IMPLIED_MDE_BETA = 0.2d;

    public TTestStatisticsService() {
        tlTsStatsUtil = ThreadLocal.withInitial(TSStatsUtil::new);
        tlNnormalDist = ThreadLocal.withInitial(NormalDistribution::new);

        // In Implied MDE calculation: alpha = 10% and beta = 20%
        zalpha4ImpliedMDE = tlNnormalDist.get().inverseCumulativeProbability(1 - IMPLIED_MDE_ALPHA / 2);
        zbeta4ImpliedMDE = tlNnormalDist.get().inverseCumulativeProbability(1 - IMPLIED_MDE_BETA);
    }

    @Override
    public List<MetricStatsSummary> calcStats(MetricSummaryGroup summaryGroup, Double params) {
        //figure out test and control
        double ctrlTrafficPct = 0d;
        int numCtrlFound = 0;
        MetricSummary combinedCtrl = null;
        List<MetricStatsSummary> result = Lists.newArrayList();
        for (PITTreatmentMetricSummary summary : summaryGroup.getSummaries()) {
            if (summary.getTreatmentType() == TreatmentType.Control) {
                ctrlTrafficPct += summary.getTrafficPct();
                if (numCtrlFound == 0) {
                    combinedCtrl = summary.getMetricSummary();
                } else if (numCtrlFound == 1) {
                    //more than one, so we copy it first
                    combinedCtrl = combinedCtrl.copy();
                    combinedCtrl.combine(summary.getMetricSummary());
                } else {
                    combinedCtrl.combine(summary.getMetricSummary());
                }
                ++numCtrlFound;
                result.add(noStatsRequired(summary.getTreatmentId(), summary.getTrafficPct(), summary.getMetricId(), summary.getMetricSummary()));
            }
        }

        for (PITTreatmentMetricSummary summary : summaryGroup.getSummaries()) {
            if (summary.getTreatmentType() == TreatmentType.Treatment) {
                result.add(calcStats(summary, combinedCtrl, ctrlTrafficPct, params));
            }
        }
        return result;
    }

    @Override
    public MetricStatsSummary calcStats(MetricSummaryGroup summaryGroup, long treatmentId, Double params) {
        //figure out test and control
        double ctrlTrafficPct = 0d;
        int numCtrlFound = 0;
        MetricSummary combinedCtrl = null;

        PITTreatmentMetricSummary treatmentSummary = null;
        for (PITTreatmentMetricSummary summary : summaryGroup.getSummaries()) {
            if (summary.getTreatmentType() == TreatmentType.Control) {
                if (summary.getTreatmentId() == treatmentId) {
                    return noStatsRequired(summary.getTreatmentId(), summary.getTrafficPct(), summary.getMetricId(), summary.getMetricSummary());
                }

                ctrlTrafficPct += summary.getTrafficPct();
                if (numCtrlFound == 0) {
                    combinedCtrl = summary.getMetricSummary();
                } else if (numCtrlFound == 1) {
                    //more than one, so we copy it first
                    combinedCtrl = combinedCtrl.copy();
                    combinedCtrl.combine(summary.getMetricSummary());
                } else {
                    combinedCtrl.combine(summary.getMetricSummary());
                }
                ++numCtrlFound;
            } else if (summary.getTreatmentId() == treatmentId) {
                treatmentSummary = summary;
            }
        }

        if (treatmentSummary != null) {
            return calcStats(treatmentSummary, combinedCtrl, ctrlTrafficPct, params);
        }

        return null;
    }

    private MetricStatsSummary calcStats(PITTreatmentMetricSummary t, MetricSummary control, double ctrlTrafficPct, Double alpha) {
        if (control == null) {
            return noStatsRequired(t.getTreatmentId(), t.getTrafficPct(), t.getMetricId(), t.getMetricSummary());
        }

        Preconditions.checkState(t.getMetricId() == control.getMetricId());
        int metricId = t.getMetricId();

        MetricSummary treatment = t.getMetricSummary();
        long trmtSampleCount = treatment.getSampleCount();
        long ctrlSampleCount = control.getSampleCount();
        boolean noStatsRequired = trmtSampleCount == 0L
                || ctrlSampleCount == 0L
                || Double.isNaN(treatment.getExtrapolatedStddev())
                || Double.isNaN(control.getExtrapolatedStddev());
        if (metricId == Constants.SAMPLE_COUNT_METRIC_ID || metricId == Constants.TGI_SAMPLE_COUNT_METRIC_ID) {
            //basically means that we do have sample count metric in the query result, we should yield it as well.
            //it is different than other no stats required field, as we need to generate cp and lift.
            MetricStatsSummary stats = noStatsRequired(t.getTreatmentId(), t.getTrafficPct(), metricId, treatment);
            double normalizedCtrl = ctrlSampleCount / ctrlTrafficPct;
            double cp = (trmtSampleCount / t.getTrafficPct() - normalizedCtrl) / normalizedCtrl;
            stats.setCp(cp);
            stats.setLift(cp * 100);
            stats.setTimestamp(treatment.getTimestamp());
            stats.setTrafficPct(t.getTrafficPct());
            stats.setImpliedMDE(-1d);
            stats.setCv(Double.NaN);
            stats.setSkewness(Double.NaN);
            stats.setKurtosis(Double.NaN);
            return stats;
        } else if (noStatsRequired) {
            return noStatsRequired(t.getTreatmentId(), t.getTrafficPct(), metricId, treatment);
        } else {
            TSStatsUtil tsStatsUtil = tlTsStatsUtil.get();
            NormalDistribution normalDist = tlNnormalDist.get();
            MetricStatsSummary stats = new MetricStatsSummary();
            stats.setMetricId(metricId);
            stats.setTreatmentId(t.getTreatmentId());
            stats.setAbsolute(treatment.getSum());
            stats.setNormalized(treatment.getExtrapolatedMean());
            stats.setMetricCount(treatment.getMetricCount());
            stats.setSampleCount(treatment.getSampleCount());

            double extrapolatedTrmtMean = treatment.getExtrapolatedMean();
            double extrapolatedTrmtStddev = treatment.getExtrapolatedStddev();
            double extrapolatedCtrlMean = control.getExtrapolatedMean();

            double controlStddev = control.getExtrapolatedStddev();
            double controlVar = controlStddev * controlStddev;
            double lift = MathUtil.eq(extrapolatedCtrlMean, 0d) ? 0d : (extrapolatedTrmtMean / extrapolatedCtrlMean - 1) * 100;
            stats.setLift(lift);
            stats.setPvalue(tsStatsUtil.calculatePValue(extrapolatedTrmtMean, extrapolatedCtrlMean,
                    extrapolatedTrmtStddev * extrapolatedTrmtStddev, controlVar, treatment.getSampleCount(),
                    control.getSampleCount()));
            stats.setStatsType(StatsAlgorithmType.TTEST);
            stats.setCp(MathUtil.eq(extrapolatedCtrlMean, 0d) ? 0d : (extrapolatedTrmtMean - extrapolatedCtrlMean) / extrapolatedCtrlMean);

            double zscore = normalDist.inverseCumulativeProbability(1 - alpha / 2);
            double halfCI = TSStatsUtil.calculateHalfConfidenceInterval(extrapolatedTrmtStddev, treatment.getSampleCount(),
                    zscore, extrapolatedCtrlMean, controlVar, control.getSampleCount());
            stats.setLowerCI(lift - halfCI);
            stats.setUpperCI(lift + halfCI);
            stats.setTimestamp(treatment.getTimestamp());
            stats.setTrafficPct(t.getTrafficPct());
            double impliedMDE = -1d;
            if (!MathUtil.eq(extrapolatedCtrlMean, 0d)) {
                // Calc Implied MDE
                // Get CV, observed CV from control group” to calculate CV_c = sigma_c / mu_c
                double controlCV = controlStddev / extrapolatedCtrlMean;
                // effective_sample_size = (controlSampleCount * treatmentSampleCount) / (controlSampleCount + treatmentSampleCount)
                impliedMDE = controlCV * (zalpha4ImpliedMDE + zbeta4ImpliedMDE) * Math.sqrt(1.0d / ctrlSampleCount + 1.0d / trmtSampleCount);
            }
            stats.setImpliedMDE(impliedMDE);
            stats.setCv(treatment.getExtrapolatedCV());
            stats.setSkewness(treatment.getExtrapolatedSkewness());
            stats.setKurtosis(treatment.getExtrapolatedKurtosis());

            return stats;
        }
    }

    private MetricStatsSummary noStatsRequired(long treatmentId, double trafficPct, int metricId, MetricSummary treatment) {
        MetricStatsSummary stats = new MetricStatsSummary();
        stats.setTreatmentId(treatmentId);
        stats.setMetricId(metricId);
        stats.setStatsType(StatsAlgorithmType.NotApplicable);

        stats.setAbsolute(treatment.getSum());
        stats.setNormalized(treatment.getExtrapolatedMean());
        stats.setMetricCount(treatment.getMetricCount());
        stats.setSampleCount(treatment.getSampleCount());
        stats.setTimestamp(treatment.getTimestamp());
        stats.setTrafficPct(trafficPct);
        stats.setImpliedMDE(-1d);
        stats.setCv(treatment.getExtrapolatedCV());
        stats.setSkewness(treatment.getExtrapolatedSkewness());
        stats.setKurtosis(treatment.getExtrapolatedKurtosis());
        return stats;
    }
}
