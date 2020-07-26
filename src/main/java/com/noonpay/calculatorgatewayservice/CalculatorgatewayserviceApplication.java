package com.noonpay.calculatorgatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
@EnableCaching
@SpringBootApplication
public class CalculatorgatewayserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculatorgatewayserviceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
