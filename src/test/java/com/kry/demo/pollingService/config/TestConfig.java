package com.kry.demo.pollingService.config;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;

public class TestConfig {

    @Bean
    TestRestTemplate testRestTemplate() {
        return new TestRestTemplate();
    }
}
