package com.kry.demo.pollingService;

import com.kry.demo.pollingService.domain.WebService;
import com.kry.demo.pollingService.entities.ServiceEntity;

import java.time.LocalDateTime;

public class TestDataProvider {

    public static WebService getOneWebService() {
        return WebService.builder().id(1L).serviceName("test service 1").url("https://test-service.com")
                .status(true).lastUpdated(LocalDateTime.now()).createdAt(LocalDateTime.now()).build();
    }

    public static ServiceEntity getOneServiceEntity() {
        return ServiceEntity.builder().serviceId(1L).serviceName("test service 1").url("https://test-service.com")
                .status(true).lastUpdated(LocalDateTime.now()).createdAt(LocalDateTime.now()).build();
    }
}
