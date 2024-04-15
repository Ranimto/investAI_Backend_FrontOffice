package com.example.notifications.Controllers;

import com.example.notifications.impl.IndicatorsApiServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/indicators")

public class IndicatorsController {
    @Autowired
    private  IndicatorsApiServiceImpl indicatorsApiService ;
    @GetMapping("/simpleMovingAverage")
    public String fetchChangePriceDataAndStore(@RequestParam List<String> symbols) {
        String stockData = indicatorsApiService.fetchSimpleMovingAverageData(symbols);
        return stockData;
    }

}
