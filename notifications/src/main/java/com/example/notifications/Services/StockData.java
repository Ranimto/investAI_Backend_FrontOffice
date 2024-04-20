package com.example.notifications.Services;

import java.util.List;

public interface StockData {

    void fetchAndStoreStockData(String symbol);
    String getStockData(String symbol);
    String fetchDataAndStore(String symbol);

    void fetchAndStoreStockDataAnalytics(String symbol , String symbol2, String calculation);
    String getStockDataAnalytic(String symbol,String symbol2 , String calculation);
    String fetchAnalyticDataAndStore(String symbol ,String symbol2  , String calculation);

    void fetchAndStoreNewsData(String symbol);
    String fetchNewsData(String symbol);

    void fetchAndStoreChangePriceData(List<String> symbols);
    String getPriceChangeData(List<String> symbols);
    String fetchPriceChangeData(List<String> symbols);

    void fetchAndStoreRunningAnalyticsData(List<String> symbols);
    String getRunningAnalyticsData(List<String> symbols);
}
