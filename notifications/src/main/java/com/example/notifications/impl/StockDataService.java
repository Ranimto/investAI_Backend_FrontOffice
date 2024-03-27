package com.example.notifications.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;

@Service
public class StockDataService {

    private static final String API_KEY = "7B2VWMKU9SVM59DQ";
    private static final String API_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=%s&interval=5min&apikey=%s";
    private static final String API_URL2 = "https://alphavantageapi.co/timeseries/running_analytics?SYMBOLS=%s,%s&RANGE=2month&INTERVAL=DAILY&OHLC=close&WINDOW_SIZE=20&CALCULATIONS=%s&apikey=%s";
    private static final String TIMESTAMP_KEY_PREFIX = "timestamp_";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // Include timestamp tracking for each stock symbol in Redis
    // This allows to determine whether a particular stock price needs to be updated based on the timestamp compared to the current time

    //
    public void fetchAndStoreStockData(String symbol) {
        String apiUrl = String.format(API_URL, symbol, API_KEY);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);

        if (response != null && !response.equals("Information: Thank you for using Alpha Vantage! Our standard API rate limit is 25 requests per day. Please subscribe to any of the premium plans at https://www.alphavantage.co/premium/ to instantly remove all daily rate limits.")) {
            LocalDateTime timestamp = LocalDateTime.now();
            String timestampKey = TIMESTAMP_KEY_PREFIX + symbol;
            redisTemplate.opsForValue().set(timestampKey, timestamp.toString());
            redisTemplate.opsForValue().set(symbol, response);
        }
    }

    public String getStockData(String symbol) {
        return redisTemplate.opsForValue().get(symbol);
    }

    public String fetchDataAndStore(String symbol) {
        String stockData = getStockData(symbol);
        String timestampKey = TIMESTAMP_KEY_PREFIX + symbol;

        // Check the timestamp
        String storedTimestampStr = redisTemplate.opsForValue().get(timestampKey);
        LocalDateTime storedTimestamp = storedTimestampStr != null ? LocalDateTime.parse(storedTimestampStr) : null;
        LocalDateTime currentTimestamp = LocalDateTime.now();

        if (stockData == null || storedTimestamp == null || storedTimestamp.plusMinutes(5).isBefore(currentTimestamp)) {
            fetchAndStoreStockData(symbol);
            stockData = getStockData(symbol);
        }
        return stockData;
    }


    // Running Mean and Running Standard Deviation data
    public void fetchAndStoreStockDataAnalytics(String symbol , String symbol2, String calculation) {
        String apiUrl = String.format(API_URL2, symbol, symbol2, calculation ,API_KEY);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);

        if (response != null && !response.equals("Information: Thank you for using Alpha Vantage! Our standard API rate limit is 25 requests per day. Please subscribe to any of the premium plans at https://www.alphavantage.co/premium/ to instantly remove all daily rate limits.")) {
            LocalDateTime timestamp = LocalDateTime.now();
            String timestampKey = TIMESTAMP_KEY_PREFIX + symbol+ symbol2+calculation;
            redisTemplate.opsForValue().set(timestampKey, timestamp.toString());
            redisTemplate.opsForValue().set(symbol+symbol2+ calculation, response);
        }
    }

    public String getStockDataAnalytic(String symbol,String symbol2 , String calculation) {
        return redisTemplate.opsForValue().get(symbol+symbol2+calculation);
    }

    public String fetchAnalyticDataAndStore(String symbol ,String symbol2  , String calculation) {
        String stockData = getStockData(symbol);
        String timestampKey = TIMESTAMP_KEY_PREFIX + symbol+ symbol2 +calculation;

        // Check the timestamp
        String storedTimestampStr = redisTemplate.opsForValue().get(timestampKey);
        LocalDateTime storedTimestamp = storedTimestampStr != null ? LocalDateTime.parse(storedTimestampStr) : null;
        LocalDateTime currentTimestamp = LocalDateTime.now();

        if (stockData == null || storedTimestamp == null || storedTimestamp.plusMinutes(5).isBefore(currentTimestamp)) {
            fetchAndStoreStockDataAnalytics(symbol,symbol2, calculation);
            stockData = getStockDataAnalytic(symbol,symbol2, calculation);
        }
        return stockData;
    }

}
