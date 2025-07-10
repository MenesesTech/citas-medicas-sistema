package com.femt.medical.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

// Para comunicacion con UserId de MedicalStaff con el servicio de usr-management service para la uteticacion
@FeignClient(name = "user-management-service")
public interface UserClient {
    @GetMapping("/api/users/profile")
    UserResponse getUserProfile(@RequestHeader("Authorization") String token);

}
