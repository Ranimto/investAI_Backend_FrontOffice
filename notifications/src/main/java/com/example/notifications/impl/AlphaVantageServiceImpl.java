package com.example.notifications.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AlphaVantageServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(AlphaVantageServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;



    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void importDataFromAlphaVantage(String symbol) {
        String apiKey = "7B2VWMKU9SVM59DQ";
        String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol + "&apikey=" + apiKey;

        // API Alpha Vantage Call
        logger.info("Calling Alpha Vantage API for symbol: {}", symbol);
        Map<String, Object> alphaVantageData = restTemplate.getForObject(apiUrl, Map.class);
        logger.info("Received data from Alpha Vantage API: {}", alphaVantageData);

        // Save DATA in Redis
        String redisKey = "alpha_vantage_data_" + symbol;
        redisTemplate.opsForValue().set(redisKey, alphaVantageData);
        logger.info("Data saved in Redis with key: {}", redisKey);
    }
}
