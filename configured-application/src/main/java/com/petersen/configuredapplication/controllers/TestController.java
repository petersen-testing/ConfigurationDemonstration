package com.petersen.configuredapplication.controllers;

import com.petersen.configuredapplication.configuration.DemoConfiguration;
import com.petersen.configuredapplication.services.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/test")
    public DemoConfiguration getDemoConfiguration() {
        return this.testService.getDemoConfiguration();
    }
}
