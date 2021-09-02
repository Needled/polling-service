package com.kry.demo.pollingService.converters;

import com.kry.demo.pollingService.domain.WebService;
import com.kry.demo.pollingService.entities.ServiceEntity;
import org.springframework.stereotype.Component;

@Component
public class ServiceConverter {

    public WebService buildDomainObjectFrom(ServiceEntity serviceEntity) {
        return WebService.builder().id(serviceEntity.getServiceId()).serviceName(serviceEntity.getServiceName())
                .url(serviceEntity.getUrl()).status(serviceEntity.isStatus())
                .createdAt(serviceEntity.getCreatedAt()).lastUpdated(serviceEntity.getLastUpdated()).build();
    }

    public ServiceEntity buildEntityFrom(WebService webService) {
        return ServiceEntity.builder().serviceId(webService.getId()).serviceName(webService.getServiceName())
                .url(webService.getUrl()).status(webService.isStatus())
                .createdAt(webService.getCreatedAt()).lastUpdated(webService.getLastUpdated()).build();
    }
}
