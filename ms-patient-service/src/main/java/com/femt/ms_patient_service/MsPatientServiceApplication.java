package com.femt.ms_patient_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsPatientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPatientServiceApplication.class, args);
	}

}
