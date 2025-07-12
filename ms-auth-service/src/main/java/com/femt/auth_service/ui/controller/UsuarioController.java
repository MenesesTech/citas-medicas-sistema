package com.femt.auth_service.ui.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.femt.auth_service.data.dto.request.AuthenticationDTO;
import com.femt.auth_service.data.dto.request.RegistroUsuarioDTO;
import com.femt.auth_service.data.dto.response.LoginResponseDTO;
import com.femt.auth_service.data.dto.response.UsuarioResponseDTO;
import com.femt.auth_service.data.model.Usuario;
import com.femt.auth_service.security.TokenService;
import com.femt.auth_service.ui.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth", produces = { "application/json" })
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody AuthenticationDTO data) {
        var credentials = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(credentials);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UsuarioResponseDTO> register(@Valid @RequestBody RegistroUsuarioDTO data) {
        var usuarioResponse = usuarioService.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
    }

    @PutMapping("/enable/{uuid}")
    public ResponseEntity<?> enableUser(@PathVariable UUID uuid) {
        usuarioService.setUserEnabled(uuid);
        return ResponseEntity.ok("Usuario habilitado");
    }

}
