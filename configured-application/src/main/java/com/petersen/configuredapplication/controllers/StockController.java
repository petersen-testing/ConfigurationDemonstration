package com.petersen.configuredapplication.controllers;

import com.petersen.configuredapplication.models.StockInformation;
import com.petersen.configuredapplication.services.StockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {

    final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/orly")
    public StockInformation getOReillyStockInformation() {
        return this.stockService.getORLYStockInformation();
    }
}
