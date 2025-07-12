package com.femt.auth_service.ui.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.femt.auth_service.data.dto.request.UpdateUsuarioDTO;
import com.femt.auth_service.data.dto.response.UsuarioListResponseDTO;
import com.femt.auth_service.ui.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/usuarios", produces = { "application/json" })
public class UsuarioCrudController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioListResponseDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UsuarioListResponseDTO> getUsuario(@PathVariable UUID uuid) {
        return ResponseEntity.ok(usuarioService.findByUUID(uuid));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UsuarioListResponseDTO> updateUsuario(
            @PathVariable UUID uuid,
            @Valid @RequestBody UpdateUsuarioDTO updateDTO) {
        return ResponseEntity.ok(usuarioService.update(uuid, updateDTO));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable UUID uuid) {
        usuarioService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}