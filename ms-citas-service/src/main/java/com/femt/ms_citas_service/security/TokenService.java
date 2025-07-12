package com.femt.ms_citas_service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

    @Value("${app.jwt.secret}")
    private String secret;

    private static final String ISSUER = "user-auth-service";

    public DatosToken extraerDatos(String token) {
        try {
            var algorithm = Algorithm.HMAC256(this.secret);
            var verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();

            var decoded = verifier.verify(token);
            UUID uuid = UUID.fromString(decoded.getSubject());
            String role = decoded.getClaim("role").asString();

            return new DatosToken(uuid, role);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token inválido", e);
        }
    }

    public record DatosToken(UUID uuid, String role) {
    }
}
