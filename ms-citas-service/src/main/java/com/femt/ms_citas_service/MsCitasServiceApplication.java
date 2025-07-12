package com.femt.ms_citas_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsCitasServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCitasServiceApplication.class, args);
	}

}