package com.kry.demo.pollingService.controllers;

import com.kry.demo.pollingService.domain.WebService;
import com.kry.demo.pollingService.domain.ServiceEnvelope;
import com.kry.demo.pollingService.errors.WebServiceNotFoundException;
import com.kry.demo.pollingService.services.PollingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/kry/api")
public class PollingServiceController {

    private final PollingService pollingService;

    @GetMapping("/services")
    public Mono<ServiceEnvelope> getServices() {
        log.info("Fetching all services");
        List<WebService> services = this.pollingService.getAllServices();
        return Mono.just(new ServiceEnvelope(services));
    }

    @PostMapping("/services")
    public Mono<ServiceEnvelope> addService(@RequestParam String serviceName, @RequestParam String url) {
        log.info("Adding a new service with name {} and url {}", serviceName, url);
        WebService service = this.pollingService.addService(serviceName, url);
        return Mono.just(new ServiceEnvelope(List.of(service)));
    }

    @PatchMapping("/services/{serviceId}")
    public Mono<ServiceEnvelope> updateService(@PathVariable Long serviceId,
                                               @RequestParam(required = false) String serviceName,
                                               @RequestParam(required = false) String url) throws WebServiceNotFoundException {
        log.info("Updating a service with id: {}", serviceId);
        log.info("Updating url: {} and/or serviceName: {}", url, serviceName);
        WebService service = this.pollingService.updateService(serviceId, serviceName, url);
        return Mono.just(new ServiceEnvelope(List.of(service)));
    }

    @DeleteMapping("/services/{serviceId}")
    public Mono<Void> deleteService(@PathVariable Long serviceId) {
        log.info("Deleting a service with id: {}", serviceId);
        this.pollingService.deleteService(serviceId);
        return Mono.empty();
    }
}
