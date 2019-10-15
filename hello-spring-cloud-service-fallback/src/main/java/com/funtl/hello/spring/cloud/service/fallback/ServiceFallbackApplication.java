package com.funtl.hello.spring.cloud.service.fallback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
public class ServiceFallbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceFallbackApplication.class, args);
	}

}
