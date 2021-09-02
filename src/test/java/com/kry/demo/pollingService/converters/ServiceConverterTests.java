package com.kry.demo.pollingService.converters;

import com.kry.demo.pollingService.TestDataProvider;
import com.kry.demo.pollingService.domain.WebService;
import com.kry.demo.pollingService.entities.ServiceEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Service converter tests")
public class ServiceConverterTests {

    private ServiceConverter serviceConverter = new ServiceConverter();

    @Test
    @DisplayName("Convert from domain object to entity and back. Results should be equal")
    public void convertFromDomainObject_convertedBack_shouldBeEqual() {

        WebService webService = TestDataProvider.getOneWebService();

        ServiceEntity serviceEntity = serviceConverter.buildEntityFrom(webService);

        WebService convertedBackService = serviceConverter.buildDomainObjectFrom(serviceEntity);

        assertThat(webService).isEqualToComparingFieldByField(convertedBackService);
    }

    @Test
    @DisplayName("Convert from entity to domain object and back. Results should be equal")
    public void convertFromEntity_convertedBack_shouldBeEqual() {

        ServiceEntity serviceEntity = TestDataProvider.getOneServiceEntity();

        WebService webService = serviceConverter.buildDomainObjectFrom(serviceEntity);

        ServiceEntity convertedBackService = serviceConverter.buildEntityFrom(webService);

        assertThat(serviceEntity).isEqualToComparingFieldByField(convertedBackService);
    }
}
