package com.kry.demo.pollingService.services;

import com.kry.demo.pollingService.converters.ServiceConverter;
import com.kry.demo.pollingService.domain.WebService;
import com.kry.demo.pollingService.entities.ServiceEntity;
import com.kry.demo.pollingService.errors.InvalidUrlException;
import com.kry.demo.pollingService.errors.WebServiceNotFoundException;
import com.kry.demo.pollingService.repositories.ServicesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PollingService {

    private final ServicesRepository servicesRepository;
    private final ServiceConverter serviceConverter;

    @Value(value = "${polling.connection.timeout:3000}")
    private Integer connectionTimeout;

    public List<WebService> getAllServices() {
        List<ServiceEntity> serviceEntities = this.servicesRepository.findAll();
        return serviceEntities.stream()
                .map(serviceConverter::buildDomainObjectFrom).collect(Collectors.toList());
    }

    public WebService addService(String serviceName, String url) {
        validateUrl(url);
        boolean status = getStatus(url);
        ServiceEntity serviceEntity = ServiceEntity.builder().serviceName(serviceName).url(url).status(status).build();
        log.info("Saving new service in DB: {}", serviceEntity.toString());
        this.servicesRepository.save(serviceEntity);
        return serviceConverter.buildDomainObjectFrom(serviceEntity);
    }

    private void validateUrl(String url) {
        // verify URL, otherwise block insertion/update in db
        String[] schemes = {"http","https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (!urlValidator.isValid(url)) {
            log.error("Invalid url encountered: " + url);
            throw new InvalidUrlException(String.format("Url:{%s} is not a valid URL", url));
        }
    }

    public void deleteService(Long serviceId) {
        this.servicesRepository.deleteById(serviceId);
    }

    public WebService updateService(Long id, String serviceName, String url) throws WebServiceNotFoundException {
        Optional<ServiceEntity> serviceEntity = this.servicesRepository.findById(id);
        serviceEntity.ifPresent(entity -> {
            if(serviceName != null) {
                entity.setServiceName(serviceName);
            }
            if(url != null) {
                validateUrl(url);
                entity.setUrl(url);
            }
            entity.setStatus(getStatus(entity.getUrl()));
            this.servicesRepository.save(entity);
        });
        if(serviceEntity.isPresent()) {
            return this.serviceConverter.buildDomainObjectFrom(serviceEntity.get());
        } else {
            log.error(String.format("Web service with id:{%d} not found in database", id));
            throw new WebServiceNotFoundException(String.format("Web service with id:{%d} not found in database", id));
        }
    }

    public boolean getStatus(String url) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(connectionTimeout);
            con.connect();

            int code = con.getResponseCode();
            if (code == 200) {
                return true;
            }
        } catch (Exception e) {
            log.error("Could not ping service with url: {}. Cause: {}", url, e.getMessage());
        }
        return false;
    }

    @Scheduled(fixedRateString = "${polling.fixedRate}")
    public void pollServices() {
        log.info("Polling all services and updating their status");
        List<ServiceEntity> services = this.servicesRepository.findAll();
        services.forEach(serviceEntity -> {
            serviceEntity.setStatus(getStatus(serviceEntity.getUrl()));
        });
        //TODO: there is a problem here, lastUpdated timestamp doesn't update if the status hasn't changed. Figure a way to fix this
        this.servicesRepository.saveAllAndFlush(services);
    }
}
