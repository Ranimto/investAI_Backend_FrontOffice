package com.example.notifications.Controllers;

import com.example.notifications.impl.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stockData")
public class StockDataController {

    @Autowired
    private StockDataService stockDataService;


    @GetMapping("/fetch/{symbol}")
    public String fetchDataAndStore(@PathVariable String symbol) {
        String stockData = stockDataService.fetchDataAndStore(symbol);
        return stockData;
    }

    @GetMapping("/fetchData/{symbol}/{symbol2}/{calculation}")
    public String fetchAnalyticDataAndStore(@PathVariable String symbol,@PathVariable String symbol2, @PathVariable String calculation ) {
        String stockData = stockDataService.fetchAnalyticDataAndStore(symbol, symbol2 ,calculation);
        return stockData;
    }

    @GetMapping("/newsData/{symbol}")
    public String fetchNewsDataAndStore(@PathVariable String symbol) {
        String stockData = stockDataService.fetchNewsData(symbol);
        return stockData;
    }

    @GetMapping("/changePriceData")
    public String fetchChangePriceDataAndStore(@RequestParam List<String> symbols) {
        String stockData = stockDataService.fetchPriceChangeData(symbols);
        return stockData;
    }

    @GetMapping("/runningAnalyticsData")
    public String fetchRunningAnalyticsDataAndStore(@RequestParam List<String> symbols) {
        String stockData = stockDataService.fetchRunningAnalyticsData(symbols);
        return stockData;
    }


}
