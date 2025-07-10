package com.femt.user.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.femt.user.dto.UserProfileDTO;
import com.femt.user.models.Usuario;
import com.femt.user.services.UsuarioService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();

        UserProfileDTO profile = new UserProfileDTO(usuario.getId(), usuario.getName(), usuario.getDni(),
                usuario.getEmail(), usuario.getRol());

        return ResponseEntity.ok(profile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> getUserById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUserById(id);
        UserProfileDTO profile = new UserProfileDTO(usuario.getId(), usuario.getName(), usuario.getDni(),
                usuario.getEmail(), usuario.getRol());
        return ResponseEntity.ok(profile);
    }

    @GetMapping
    public ResponseEntity<List<UserProfileDTO>> getAllUsers() {
        List<UserProfileDTO> users = usuarioService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}