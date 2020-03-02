package com.petersen.configuredapplication.services;

import com.petersen.configuredapplication.configuration.DemoConfiguration;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    final DemoConfiguration demoConfiguration;

    public TestService(DemoConfiguration demoConfiguration) {
        this.demoConfiguration = demoConfiguration;
    }

    public DemoConfiguration getDemoConfiguration() {
        return new DemoConfiguration(demoConfiguration.getUrl(), demoConfiguration.getEnv(), demoConfiguration.getTransientValue(), demoConfiguration.isNewStockServiceEnabled());
    }

}
