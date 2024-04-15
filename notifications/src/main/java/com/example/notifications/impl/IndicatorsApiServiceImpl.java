package com.example.notifications.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndicatorsApiServiceImpl {
    private static final String API_KEY = "7B2VWMKU9SVM59DQ";
    private static final String API_URL = "https://www.alphavantage.co/query?function=SMA&symbol=%s&interval=weekly&time_period=10&series_type=open&apikey=%s";
    private static final String TIMESTAMP_KEY_PREFIX = "timestamp_";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void fetchAndStoreSimpleMovingAverageData(List<String> symbols) {
        RestTemplate restTemplate = new RestTemplate();
        String key = "SimpleMovingAverage_";
        List<String> symbolsPriceChange = new ArrayList<>();
        for (String symbol : symbols) {
            String apiUrl = String.format(API_URL, symbol, API_KEY);
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

    public String getSimpleMovingAverageData(List<String> symbols) {
        String key="SimpleMovingAverage_";
        for (String symbol: symbols) { key+=symbol+"," ;}
        return redisTemplate.opsForValue().get(key);
    }


    public String fetchSimpleMovingAverageData(List<String> symbols) {
        String key="SimpleMovingAverage_";
        for (String symbol: symbols) { key+=symbol+"," ;}
        String stockData = getSimpleMovingAverageData(symbols);

        // Check if data exists in Redis
        if (stockData != null) {return stockData;}

        String timestampKey = TIMESTAMP_KEY_PREFIX + key;

        // Check the timestamp
        String storedTimestampStr = redisTemplate.opsForValue().get(timestampKey);
        LocalDateTime storedTimestamp = storedTimestampStr != null ? LocalDateTime.parse(storedTimestampStr) : null;
        LocalDateTime currentTimestamp = LocalDateTime.now();

        if (stockData == null || storedTimestamp == null || storedTimestamp.plusMinutes(5).isBefore(currentTimestamp)) {
            fetchAndStoreSimpleMovingAverageData(symbols);
            stockData = getSimpleMovingAverageData(symbols);
        }
        return stockData;
    }
}