package com.kry.demo.pollingService.controllers;

import com.kry.demo.pollingService.domain.ServiceEnvelope;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sqlScripts/services-schema.sql"),
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sqlScripts/services-data.sql"),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sqlScripts/drop-schema.sql")
})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Polling service integration tests")
public class PollingServiceIntegrationTests {

    private final String API_PATH = "/kry/api/services";

    @Autowired
    private TestRestTemplate testRestTemplate;
    private RestTemplate patchRestTemplate;

    @Test
    @DisplayName("Get services, expected ok")
    public void getServices_shouldReturnAvailableServices() {

        ResponseEntity<ServiceEnvelope> responseEntity = testRestTemplate.getForEntity(API_PATH, ServiceEnvelope.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getServices().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Add service, expected ok")
    public void addService_shouldReturnNewlyAddedService() {

        String serviceName = "coolService";
        String url = "https://cool-service.net";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<ServiceEnvelope> responseEntity = testRestTemplate.postForEntity(API_PATH + "?serviceName=" + serviceName +"&url=" + url, request, ServiceEnvelope.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getServices().size()).isEqualTo(1);
        assertThat(responseEntity.getBody().getServices().get(0).getServiceName()).isEqualTo(serviceName);
        assertThat(responseEntity.getBody().getServices().get(0).getUrl()).isEqualTo(url);
    }

    @Test
    @DisplayName("Add service, expected bad request for invalid url")
    public void addService_shouldThrowAnErrorIfUrlIsInvalid() {

        String serviceName = "coolService";
        String url = "not a valid url";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<ServiceEnvelope> responseEntity = testRestTemplate.postForEntity(API_PATH + "?serviceName=" + serviceName +"&url=" + url, request, ServiceEnvelope.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Update service, expected ok")
    public void updateService_shouldReturnNewlyUpdatedService() {

        Integer serviceId = 3;
        String serviceName = "coolService";
        String url = "https://cool-service.net";

        patchRestTemplate = testRestTemplate.getRestTemplate();
        patchRestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);
        ServiceEnvelope envelope = patchRestTemplate.patchForObject(API_PATH + "/" + serviceId + "?serviceName=" + serviceName + "&url=" + url, request, ServiceEnvelope.class);

        assertThat(envelope.getServices().size()).isEqualTo(1);
        assertThat(envelope.getServices().get(0).getServiceName()).isEqualTo(serviceName);
        assertThat(envelope.getServices().get(0).getUrl()).isEqualTo(url);
    }

    @Test
    @DisplayName("Delete service, expected ok")
    public void deleteService_shouldReturnOkStatus() {

        Integer serviceId = 3;

        testRestTemplate.delete(API_PATH + "/" + serviceId);
        ResponseEntity<ServiceEnvelope> responseEntity = testRestTemplate.getForEntity(API_PATH, ServiceEnvelope.class);

        assertThat(responseEntity.getBody().getServices().size()).isEqualTo(2);
    }
}
