package com.kry.demo.pollingService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PollingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PollingServiceApplication.class, args);
	}

}
