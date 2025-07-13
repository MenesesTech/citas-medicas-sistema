package com.femt.ms_citas_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                return httpSecurity
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth
                                                // Endpoints públicos
                                                .requestMatchers(HttpMethod.POST, "/api/citas/**", "/api/recetas/**")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.DELETE, "/api/citas/**", "/api/recetas/**")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/citas/**", "/api/recetas/**")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.PUT, "/api/citas/**", "/api/recetas/**")
                                                .permitAll()
                                                // Swagger acceso libre
                                                .requestMatchers(
                                                                "/v3/api-docs/**",
                                                                "/swagger-ui/**",
                                                                "/swagger-ui.html",
                                                                "/swagger-resources/**",
                                                                "/webjars/**")
                                                .permitAll()

                                                // Todo lo demás requiere autenticación
                                                .anyRequest().authenticated())
                                .build();
        }
}
