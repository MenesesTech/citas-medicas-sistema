package com.femt.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

        @Bean
        public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
                return builder.routes()
                                .route("ms-auth-service", r -> r.path("/api/auth/**")
                                                .uri("lb://ms-auth-service"))
                                .route("ms-patient-service", r -> r.path("/api/patients/**")
                                                .uri("lb://ms-patient-service"))
                                .build();
        }
}