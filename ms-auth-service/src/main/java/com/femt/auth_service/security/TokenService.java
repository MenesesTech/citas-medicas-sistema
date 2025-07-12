package com.femt.auth_service.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.femt.auth_service.data.model.Usuario;

@Service
public class TokenService {
    @Value("${app.jwt.secret}")
    private String secret;

    private static final String ISSUER = "user-auth-service";

    public String generateToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(String.valueOf(usuario.getUsuarioUUID()))
                    .withClaim("role", usuario.getRol().name())
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error al generar token", e);
        }
    }

    /**
     * Valida un token JWT recibido en las solicitudes.
     * 
     * - Verifica la firma del token usando la misma clave secreta.
     * - Confirma que el token fue emitido por el servicio correcto.
     * - Devuelve el `subject` (id del usuario) si el token es válido.
     * - Si el token es inválido, devuelve una cadena vacía.
     *
     * @param token Token JWT a validar.
     * @return Email del usuario si es válido, o cadena vacía si es inválido.
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    /**
     * Genera la fecha de expiración para el token.
     * 
     * - Añade 2 horas a la hora local del sistema.
     * - Usa zona horaria UTC-5 (Perú).
     *
     * @return Instante de expiración como objeto Instant.
     */
    public Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}
