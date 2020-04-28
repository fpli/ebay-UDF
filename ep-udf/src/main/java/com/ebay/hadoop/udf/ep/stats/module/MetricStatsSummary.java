package com.ebay.hadoop.udf.ep.stats.module;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.ToString;

import java.util.Map;

/**
 *
 * @author bingqxu
 */
@ToString
public class MetricStatsSummary {
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(as = Long.class)
    private long treatmentId;
    private double trafficPct;
    private long timestamp;

    private int metricId;
    private double absolute;
    private double normalized;
    private long sampleCount;
    private long metricCount;

    private StatsAlgorithmType statsType;

    private double lift;
    private double cp;

    //for t-test
    private double pvalue;
    private double upperCI;
    private double lowerCI;

    //for mab
    private long betasAlpha;
    private long betasBeta;
    private double optimalArmProbability;
    private double betaCProbability;
    private double liftMode;
    private double liftBciLower;
    private double liftBciUpper;
    private boolean liftBciNoZero;
    private boolean isWinner;
    private double pvrDaily;
    private Map<Double, Integer> densityMap;

    public long getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(long treatmentId) {
        this.treatmentId = treatmentId;
    }

    public double getTrafficPct() {
        return trafficPct;
    }

    public void setTrafficPct(double trafficPct) {
        this.trafficPct = trafficPct;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getMetricId() {
        return metricId;
    }

    public void setMetricId(int metricId) {
        this.metricId = metricId;
    }

    public double getAbsolute() {
        return absolute;
    }

    public void setAbsolute(double absolute) {
        this.absolute = absolute;
    }

    public double getNormalized() {
        return normalized;
    }

    public void setNormalized(double normalized) {
        this.normalized = normalized;
    }

    public long getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(long sampleCount) {
        this.sampleCount = sampleCount;
    }

    public long getMetricCount() {
        return metricCount;
    }

    public void setMetricCount(long metricCount) {
        this.metricCount = metricCount;
    }

    public StatsAlgorithmType getStatsType() {
        return statsType;
    }

    public void setStatsType(StatsAlgorithmType statsType) {
        this.statsType = statsType;
    }

    public double getLift() {
        return lift;
    }

    public void setLift(double lift) {
        this.lift = lift;
    }

    public double getCp() {
        return cp;
    }

    public void setCp(double cp) {
        this.cp = cp;
    }

    public double getPvalue() {
        return pvalue;
    }

    public void setPvalue(double pvalue) {
        this.pvalue = pvalue;
    }

    public double getUpperCI() {
        return upperCI;
    }

    public void setUpperCI(double upperCI) {
        this.upperCI = upperCI;
    }

    public double getLowerCI() {
        return lowerCI;
    }

    public void setLowerCI(double lowerCI) {
        this.lowerCI = lowerCI;
    }

    public long getBetasAlpha() {
        return betasAlpha;
    }

    public void setBetasAlpha(long betasAlpha) {
        this.betasAlpha = betasAlpha;
    }

    public long getBetasBeta() {
        return betasBeta;
    }

    public void setBetasBeta(long betasBeta) {
        this.betasBeta = betasBeta;
    }

    public double getOptimalArmProbability() {
        return optimalArmProbability;
    }

    public void setOptimalArmProbability(double optimalArmProbability) {
        this.optimalArmProbability = optimalArmProbability;
    }

    public double getBetaCProbability() {
        return betaCProbability;
    }

    public void setBetaCProbability(double betaCProbability) {
        this.betaCProbability = betaCProbability;
    }

    public double getLiftMode() {
        return liftMode;
    }

    public void setLiftMode(double liftMode) {
        this.liftMode = liftMode;
    }

    public double getLiftBciLower() {
        return liftBciLower;
    }

    public void setLiftBciLower(double liftBciLower) {
        this.liftBciLower = liftBciLower;
    }

    public double getLiftBciUpper() {
        return liftBciUpper;
    }

    public void setLiftBciUpper(double liftBciUpper) {
        this.liftBciUpper = liftBciUpper;
    }

    public boolean isLiftBciNoZero() {
        return liftBciNoZero;
    }

    public void setLiftBciNoZero(boolean liftBciNoZero) {
        this.liftBciNoZero = liftBciNoZero;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public double getPvrDaily() {
        return pvrDaily;
    }

    public void setPvrDaily(double pvrDaily) {
        this.pvrDaily = pvrDaily;
    }

    public Map<Double, Integer> getDensityMap() {
        return densityMap;
    }

    public void setDensityMap(Map<Double, Integer> densityMap) {
        this.densityMap = densityMap;
    }
}
