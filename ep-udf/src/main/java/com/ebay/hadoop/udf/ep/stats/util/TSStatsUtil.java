package com.ebay.hadoop.udf.ep.stats.util;

import org.apache.commons.math3.stat.inference.TTest;

import java.text.DecimalFormat;

/**
 *
 * @author bingqxu
 */
public class TSStatsUtil extends TTest {
    private static final DecimalFormat doubleFormatter6Places = new DecimalFormat("#.######");
    private static final DecimalFormat doubleFormatter2Places = new DecimalFormat("#.##");

    public static double calculateHalfConfidenceInterval(double trmtStddev, long wmTreatmentSize, double zscore, double ctrlMean,
                                                         double ctrlVar, long controlSize) {
        double treatmentStddev = Double.isNaN(trmtStddev) ? trmtStddev : Double.parseDouble(doubleFormatter6Places.format(trmtStddev));
        double controlMean = Double.isNaN(ctrlMean) ? ctrlMean : Double.parseDouble(doubleFormatter6Places.format(ctrlMean));
        double controlVar = Double.isNaN(ctrlVar) ? ctrlVar : Double.parseDouble(doubleFormatter6Places.format(ctrlVar));

        double expr1 = controlSize != 0 ? controlVar / controlSize : 0.0;
        double expr2 = wmTreatmentSize != 0 ? Math.pow(treatmentStddev, 2) / wmTreatmentSize : 0.0;
        double expr3 = MathUtil.ne(controlMean, 0) ? Math.sqrt(expr1 + expr2) / controlMean : 0.0;
        double ci = 100 * zscore * expr3;
        double finalCi = Double.isNaN(ci) ? 0.0 : ci;
        return Double.parseDouble(doubleFormatter2Places.format(finalCi));
    }

    public double calculatePValue(double testMean, double ctrlMean, double testVar,
                                  double ctrlVar, double testN, double ctrlN) {
        double pvalue = tTest(testMean, ctrlMean, testVar, ctrlVar, testN, ctrlN);
        double finalPValue = Double.isNaN(pvalue) ? -777 : pvalue;
        return Double.parseDouble(doubleFormatter6Places.format(finalPValue));
    }

    public static double getMean(double total, long count) {
        return count == 0 ? 0d : total / count;
    }

    public static double calculateExtrapolatedStddev(double treatmentMean, double treatmentStddev, long metricCnt, double extrapolatedMean,
                                                     long sampleCnt) {
        double expr1 = (treatmentStddev * treatmentStddev) * (metricCnt - 1);
        double expr2 = treatmentMean * treatmentMean * metricCnt;
        double expr3 = extrapolatedMean * extrapolatedMean * sampleCnt;
        double numerator = (expr1 + expr2) - expr3;
        double denominator = sampleCnt - 1d;
        double extrapolatedStddev = (MathUtil.ne(denominator, 0)) ? Math.sqrt(numerator / denominator) : 0.0;
        return Double.isNaN(extrapolatedStddev) ? 0 : extrapolatedStddev;
    }

    public static double calculateExtrapolatedStddev(double sumOfSquares, double extrapolatedMean, long sampleCount) {
        if (sampleCount <= 1) {
            return 0d;
        }
        double extrapolatedStddev = Math.sqrt((sumOfSquares - extrapolatedMean * extrapolatedMean * sampleCount) / (sampleCount - 1));
        return Double.isNaN(extrapolatedStddev) ? 0d : extrapolatedStddev;
    }

    public static double calculateExtrapolatedSkewness(double sumOfSquares, double sumOfCubes, double extrapolatedMean, long sampleCount) {
        if (sampleCount <= 2 || sumOfSquares < 0 || Double.isInfinite(sumOfCubes) || Double.compare(sumOfCubes,-777d) == 0) {
            return Double.NaN;
        }
        double doubleSampleCount = (double) sampleCount ;
        double expr1 = Math.sqrt(doubleSampleCount * (doubleSampleCount - 1d));
        double expr2 = doubleSampleCount - 2d;
        double expr3 = (sumOfCubes - 3d * extrapolatedMean * sumOfSquares
                + 2d * doubleSampleCount * Math.pow(extrapolatedMean, 3)) / doubleSampleCount;
        double expr4 = Math.pow((sumOfSquares - extrapolatedMean * extrapolatedMean * doubleSampleCount)
                / (doubleSampleCount), 1.5d);
        if (Double.isNaN(expr4) || expr4 == 0d) {
            return Double.NaN;
        }
        double extrapolatedSkewness = (expr1 / expr2) * (expr3 / expr4);
        return extrapolatedSkewness;
    }

    public static double calculateExtrapolatedKurtosis(double sumOfSquares, double sumOfCubes,
                                                       double sumOfTheFourthPower, double extrapolatedMean, long sampleCount) {
        if (sampleCount <= 3 || sumOfTheFourthPower < 0 || sumOfSquares < 0) {
            return Double.NaN;
        }
        double doubleSampleCount = (double) sampleCount ;
        double expr1 = (doubleSampleCount * (doubleSampleCount + 1d))
                / ((doubleSampleCount - 1d) * (doubleSampleCount - 2d) * (doubleSampleCount - 3d));
        double expr2 = sumOfTheFourthPower - 4 * extrapolatedMean * sumOfCubes
                + 6d * extrapolatedMean * extrapolatedMean * sumOfSquares
                - 3 * doubleSampleCount * Math.pow(extrapolatedMean, 4);
        double expr3 = Math.pow((sumOfSquares - extrapolatedMean * extrapolatedMean * doubleSampleCount)
                / (doubleSampleCount - 1d), 2);
        double expr4 = 3d * Math.pow((doubleSampleCount - 1d), 2)
                / ((doubleSampleCount - 2d) * (doubleSampleCount - 3));

        double extrapolatedKurtosis = expr1 * (expr2 / expr3) - expr4;
        return extrapolatedKurtosis;
    }

    public static double getVariance(double sum, double sumOfSquare, long count) {
        if (count <= 1) {
            return 0d;
        }
        double mean = getMean(sum, count);
        return (sumOfSquare + count * mean * mean - 2 * mean * sum) / (count - 1);
    }
}
