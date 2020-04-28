package com.ebay.hadoop.udf.ep.stats.util;

/**
 * Util class for Math related ops.
 *
 * @author bingqxu
 */
public class MathUtil {
    public static boolean eq(double a, double b) {
        return Math.abs(a - b) < 1e-9;
    }

    public static boolean ne(double a, double b) {
        return !eq(a, b);
    }

    public static boolean lt(double a, double b) {
        return b - a > 1e-9;
    }
}
