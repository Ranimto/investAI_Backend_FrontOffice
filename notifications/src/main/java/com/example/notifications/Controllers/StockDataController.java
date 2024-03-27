package com.example.notifications.Controllers;

import com.example.notifications.impl.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
