package com.ebay.hadoop.udf.ep.stats.module;


import com.ebay.hadoop.udf.ep.stats.TSStatisticsService;
import com.ebay.hadoop.udf.ep.stats.TTestStatisticsService;
import com.ebay.hadoop.udf.ep.stats.UnsupportedStatisticsService;

/**
 * @author weifang.
 */
public enum StatsAlgorithmType {
    TTEST(new TTestStatisticsService()),
    NotApplicable(new UnsupportedStatisticsService());

    private final TSStatisticsService<?> statisticsService;

    StatsAlgorithmType(TSStatisticsService<?> statisticsService) {
        this.statisticsService = statisticsService;
    }

    public TSStatisticsService getStatisticsService() {
        return statisticsService;
    }
}
