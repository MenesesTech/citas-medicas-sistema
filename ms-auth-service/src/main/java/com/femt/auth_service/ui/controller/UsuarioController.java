package com.femt.auth_service.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.femt.auth_service.data.dto.request.AuthenticationDTO;
import com.femt.auth_service.data.dto.request.RegistroUsuarioDTO;
import com.femt.auth_service.data.dto.request.UpdateUsuarioDTO;
import com.femt.auth_service.data.dto.response.ListarUsuarioResponseDTO;
import com.femt.auth_service.data.dto.response.LoginResponseDTO;
import com.femt.auth_service.data.dto.response.RegistroResponseDTO;
import com.femt.auth_service.ui.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth", produces = { "application/json" })
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody AuthenticationDTO data) {
        var email = data.email();
        var password = data.password();
        var token = usuarioService.login(email, password);
        return ResponseEntity.ok(token);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RegistroResponseDTO> register(@Valid @RequestBody RegistroUsuarioDTO data) {
        RegistroResponseDTO response = usuarioService.registro(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update-user/{entidadId}")
    public ResponseEntity<Void> update(@PathVariable Long entidadId, @Valid @RequestBody UpdateUsuarioDTO data) {
        usuarioService.actualizarEmailPassword(entidadId, data);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<ListarUsuarioResponseDTO>> listar() {
        var usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

}
