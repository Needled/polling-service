package com.kry.demo.pollingService.repositories;

import com.kry.demo.pollingService.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends JpaRepository<ServiceEntity, Long> {
}
