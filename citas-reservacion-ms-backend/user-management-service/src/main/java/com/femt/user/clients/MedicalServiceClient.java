package com.femt.user.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "medical-staff-service", path = "/api/medical-staff")
public interface MedicalServiceClient {

}
