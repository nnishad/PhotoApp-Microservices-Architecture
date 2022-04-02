package com.nikhilnishad.discoveryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PhotoAppdiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppdiscoveryServiceApplication.class, args);
	}

}
