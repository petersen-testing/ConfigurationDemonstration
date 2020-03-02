package com.petersen.configuredapplication.models;

import lombok.Data;

@Data
public class StockInformation {
    private String open, high, low, marketCapitalization, priceToEarningsRatio, updated;
}
