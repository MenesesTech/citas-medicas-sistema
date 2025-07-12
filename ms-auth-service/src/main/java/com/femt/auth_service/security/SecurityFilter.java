package com.femt.auth_service.security;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.femt.auth_service.data.repository.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Recuperar el token del encabezado Authorization
        var token = recoverToken(request);

        if (token != null) {
            // Validar y extraer el UUID del subject del token
            var subject = tokenService.validateToken(token);

            if (!subject.isEmpty()) {
                UUID uuid = UUID.fromString(subject);

                // Buscar el usuario autenticado
                UserDetails usuario = usuarioRepository.findByUsuarioUUID(uuid)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

                // Crear token de autenticación
                var authentication = new UsernamePasswordAuthenticationToken(
                        usuario,
                        null,
                        usuario.getAuthorities());

                // Establecer el usuario autenticado en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    /**
     * Método privado para extraer el token JWT del encabezado Authorization.
     * 
     * El token debe venir en el formato:
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVC...
     *
     * @param request Objeto de la solicitud HTTP.
     * @return El token sin el prefijo "Bearer ", o null si no está presente.
     */
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return null;
        return authHeader.replace("Bearer ", "");
    }

}
