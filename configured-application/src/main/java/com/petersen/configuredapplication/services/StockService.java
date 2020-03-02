package com.petersen.configuredapplication.services;

import com.petersen.configuredapplication.configuration.DemoConfiguration;
import com.petersen.configuredapplication.models.StockInformation;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class StockService {

    final DemoConfiguration demoConfiguration;

    private StockInformation lastRequest = null;

    private static final long cacheTTL = 60000L;

    private long lastRequestExpires = 0L;

    private static final String GOOGLE_STOCK_PAGE = "https://www.google.com/search?q=ORLY%20stock",
            OPEN_LABEL = "Open",
            HIGH_LABEL = "High",
            LOW_LABEL = "Low",
            MARKET_CAP_LABEL = "Mkt cap",
            PE_LABEL = "P/E ratio",
            UPDATED_TIME_SELECTOR = "span[class$=\"IC4HI\"]",
            UPDATED_POSTFIX_SELECTOR = " ·",
            EMPTY_STRING = "",
            TABLE_SELECTOR = "div.ZSM8k",
            TABLE_ROW_SELECTOR = "tr",
            ROW_CONTAINER_SELECTOR = "td.JgXcPd",
            ROW_LABEL_SELECTOR = "td.iyjjgb";

    public StockService(DemoConfiguration demoConfiguration) {
        this.demoConfiguration = demoConfiguration;
    }

    public StockInformation getORLYStockInformation() {
        if (this.demoConfiguration.isNewStockServiceEnabled()) {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
        return this.getOReillyStockInformation();
    }

    private StockInformation getOReillyStockInformation() {
        if (lastRequest == null || lastRequestExpires < System.currentTimeMillis()) {
            final StockInformation stockInformation = new StockInformation();
            try {
                final Document document = Jsoup.connect(GOOGLE_STOCK_PAGE).get();
                stockInformation.setUpdated(document.select(UPDATED_TIME_SELECTOR).text().replace(UPDATED_POSTFIX_SELECTOR, EMPTY_STRING));
                final Elements table = document.select(TABLE_SELECTOR);
                for (Element next : table.select(TABLE_ROW_SELECTOR)) {
                    switch (next.select(ROW_CONTAINER_SELECTOR).text()) {
                        case OPEN_LABEL:
                            stockInformation.setOpen(next.select(ROW_LABEL_SELECTOR).text().trim());
                        case HIGH_LABEL:
                            stockInformation.setHigh(next.select(ROW_LABEL_SELECTOR).text().trim());
                        case LOW_LABEL:
                            stockInformation.setLow(next.select(ROW_LABEL_SELECTOR).text().trim());
                        case MARKET_CAP_LABEL:
                            stockInformation.setMarketCapitalization(next.select(ROW_LABEL_SELECTOR).text().trim());
                        case PE_LABEL:
                            stockInformation.setPriceToEarningsRatio(next.select(ROW_LABEL_SELECTOR).text().trim());
                    }
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            this.lastRequest = stockInformation;
            this.lastRequestExpires = System.currentTimeMillis() + cacheTTL;
        }
        return this.lastRequest;
    }

}
