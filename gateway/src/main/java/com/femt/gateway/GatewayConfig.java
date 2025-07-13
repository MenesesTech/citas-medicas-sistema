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
                                .route("auth-service", r -> r.path("/api/auth/**")
                                                .uri("lb://ms-auth-service"))
                                .route("usuarios", r -> r.path("/api/usuarios/**")
                                                .filters(f -> f.stripPrefix(0))
                                                .uri("lb://ms-auth-service"))
                                .route("pacientes", r -> r.path("/api/pacientes/**")
                                                .filters(f -> f.stripPrefix(0))
                                                .uri("lb://ms-patient-service"))
                                .route("medicos", r -> r.path("/api/medicos/**")
                                                .filters(f -> f.stripPrefix(0))
                                                .uri("lb://ms-medico-service"))
                                .route("citas", r -> r.path("/api/citas/**")
                                                .filters(f -> f.stripPrefix(0))
                                                .uri("lb://ms-citas-service"))
                                .build();
        }
}
