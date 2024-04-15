package com.example.notifications.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StockDataService {

    private static final String API_KEY = "7B2VWMKU9SVM59DQ";
    private static final String API_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=%s&interval=5min&apikey=%s";
    private static final String API_URL2 = "https://alphavantageapi.co/timeseries/running_analytics?SYMBOLS=%s,%s&RANGE=2month&INTERVAL=DAILY&OHLC=close&WINDOW_SIZE=20&CALCULATIONS=%s&apikey=%s";
    private static final String API_URL3 = "https://www.alphavantage.co/query?function=NEWS_SENTIMENT&tickers=AAPL&apikey=%s";
    private static final String API_URL4 = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=%s";
    private static final String API_URL5 = "https://alphavantageapi.co/timeseries/analytics?SYMBOLS=%s,%s,%s,%s,%s&RANGE=2024-03-30&RANGE=2024-04-02&INTERVAL=DAILY&OHLC=close&CALCULATIONS=MEAN,STDDEV,CORRELATION&apikey=7B2VWMKU9SVM59DQ";
    private static final String TIMESTAMP_KEY_PREFIX = "timestamp_";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // Include timestamp tracking for each stock symbol in Redis
    // This allows to determine whether a particular stock price needs to be updated based on the timestamp compared to the current time

    //
    public void fetchAndStoreStockData(String symbol) {
        String apiUrl = String.format(API_URL,symbol, API_KEY);
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

        // Check if data exists in Redis
        if (stockData != null) {return stockData;}

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

        if (response != null && !response.contains("API key is either invalid or you have exhausted your quota for this key!")) {
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
        String stockData = getStockDataAnalytic(symbol, symbol2, calculation);

        // Check if data exists in Redis
        if (stockData != null) {
            return stockData;
        }

        String timestampKey = TIMESTAMP_KEY_PREFIX + symbol + symbol2 + calculation;

        // Check the timestamp
        String storedTimestampStr = redisTemplate.opsForValue().get(timestampKey);
        LocalDateTime storedTimestamp = storedTimestampStr != null ? LocalDateTime.parse(storedTimestampStr) : null;
        LocalDateTime currentTimestamp = LocalDateTime.now();

        if (stockData == null || storedTimestamp == null || storedTimestamp.plusMinutes(5).isBefore(currentTimestamp)) {
            fetchAndStoreStockDataAnalytics(symbol, symbol2, calculation);
            stockData = getStockDataAnalytic(symbol, symbol2, calculation);
        }
        return stockData;
    }


    // News data

    public void fetchAndStoreNewsData(String symbol) {
        String apiUrl = String.format(API_URL3,symbol, API_KEY);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);

        if (response != null && !response.equals("The **demo** API key is for demo purposes only. Please claim your free API key at (https://www.alphavantage.co/support/#api-key) to explore our full API offerings. It takes fewer than 20 seconds.")) {
            LocalDateTime timestamp = LocalDateTime.now();

            String timestampKey = TIMESTAMP_KEY_PREFIX + symbol+"_News";
            redisTemplate.opsForValue().set(timestampKey, timestamp.toString());
            redisTemplate.opsForValue().set(symbol+"_News", response);
        }
    }

    public String getNewsData(String symbol) {
        return redisTemplate.opsForValue().get(symbol+"_News");
    }

    public String fetchNewsData(String symbol) {
        String stockData = getStockData(symbol+"_News");

        // Check if data exists in Redis
        if (stockData != null) {return stockData;}

        String timestampKey = TIMESTAMP_KEY_PREFIX + symbol+"_News";

        // Check the timestamp
        String storedTimestampStr = redisTemplate.opsForValue().get(timestampKey);
        LocalDateTime storedTimestamp = storedTimestampStr != null ? LocalDateTime.parse(storedTimestampStr) : null;
        LocalDateTime currentTimestamp = LocalDateTime.now();

        if (stockData == null || storedTimestamp == null || storedTimestamp.plusMinutes(5).isBefore(currentTimestamp)) {
            fetchAndStoreNewsData(symbol);
            stockData = getNewsData(symbol);
        }
        return stockData;
    }



    //Change price data

    public void fetchAndStoreChangePriceData(List<String> symbols) {
        RestTemplate restTemplate = new RestTemplate();
        String key = "";
        List<String> symbolsPriceChange = new ArrayList<>();
        for (String symbol : symbols) {
            String apiUrl = String.format(API_URL4, symbol, "BM9262Y3FRRP3S6T");
            String response = restTemplate.getForObject(apiUrl, String.class);
            if (response != null && !response.equals("The **demo** API key is for demo purposes only. Please claim your free API key at (https://www.alphavantage.co/support/#api-key) to explore our full API offerings. It takes fewer than 20 seconds.")) {
                key += symbol + ",";
                symbolsPriceChange.add(response);
            }
        }
            LocalDateTime timestamp = LocalDateTime.now();
            String timestampKey = TIMESTAMP_KEY_PREFIX + key;
            redisTemplate.opsForValue().set(timestampKey, timestamp.toString());
            redisTemplate.opsForValue().set(key, String.valueOf(symbolsPriceChange));
    }

    public String getPriceChangeData(List<String> symbols) {
        String key="";
        for (String symbol: symbols) { key+=symbol+"," ;}
        return redisTemplate.opsForValue().get(key);
    }

    public String fetchPriceChangeData(List<String> symbols) {
        String key="";
        for (String symbol: symbols) { key+=symbol+"," ;}
        String stockData = getPriceChangeData(symbols);

        // Check if data exists in Redis
        if (stockData != null) {return stockData;}

        String timestampKey = TIMESTAMP_KEY_PREFIX + key;

        // Check the timestamp
        String storedTimestampStr = redisTemplate.opsForValue().get(timestampKey);
        LocalDateTime storedTimestamp = storedTimestampStr != null ? LocalDateTime.parse(storedTimestampStr) : null;
        LocalDateTime currentTimestamp = LocalDateTime.now();

        if (stockData == null || storedTimestamp == null || storedTimestamp.plusMinutes(5).isBefore(currentTimestamp)) {
            fetchAndStoreChangePriceData(symbols);
            stockData = getPriceChangeData(symbols);
        }
        return stockData;
    }

    //Change RunningAnalytics
    //every api_url contain 5 symbols

    public void fetchAndStoreRunningAnalyticsData(List<String> symbols) {
        String key = "AnalyticsData";
        for ( String symbol :symbols) {key+=symbol+",";}
        String apiUrl = String.format(API_URL5,symbols.get(0),symbols.get(1),symbols.get(2),symbols.get(3),symbols.get(4), API_KEY);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);

        if (response != null && !response.contains( "API key is either invalid or you have exhausted your quota for this key!")) {
            LocalDateTime timestamp = LocalDateTime.now();

            String timestampKey = TIMESTAMP_KEY_PREFIX + key;
            redisTemplate.opsForValue().set(timestampKey, timestamp.toString());
            redisTemplate.opsForValue().set(key, response);
        }
    }

    public String getRunningAnalyticsData(List<String> symbols) {
        String key="AnalyticsData";
        for (String symbol: symbols) { key+=symbol+"," ;}
        return redisTemplate.opsForValue().get(key);
    }

    public String fetchRunningAnalyticsData(List<String> symbols) {
        String key="AnalyticsData";
        for (String symbol: symbols) { key+=symbol+"," ;}
        String stockData = getRunningAnalyticsData(symbols);
        log.info("stockData", stockData);
        // Check if data exists in Redis
        if (stockData != null) {return stockData;}

        String timestampKey = TIMESTAMP_KEY_PREFIX + key;

        // Check the timestamp
        String storedTimestampStr = redisTemplate.opsForValue().get(timestampKey);
        LocalDateTime storedTimestamp = storedTimestampStr != null ? LocalDateTime.parse(storedTimestampStr) : null;
        LocalDateTime currentTimestamp = LocalDateTime.now();

        if (stockData == null || storedTimestamp == null || storedTimestamp.plusMinutes(5).isBefore(currentTimestamp)) {
            fetchAndStoreRunningAnalyticsData(symbols);
            stockData = getRunningAnalyticsData(symbols);
        }
        return stockData;
    }
}
