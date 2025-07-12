package com.femt.auth_service.ui.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.femt.auth_service.data.dto.request.RegistroUsuarioDTO;
import com.femt.auth_service.data.dto.request.UpdateUsuarioDTO;
import com.femt.auth_service.data.dto.response.UsuarioListResponseDTO;
import com.femt.auth_service.data.dto.response.UsuarioResponseDTO;
import com.femt.auth_service.data.model.Usuario;
import com.femt.auth_service.data.repository.UsuarioRepository;
import com.femt.auth_service.security.TokenService;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder bPasswordEncoder;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.usuarioRepository.findByEmail(email).orElseThrow();
    }

    public UsuarioResponseDTO save(RegistroUsuarioDTO registroUsuarioDTO) {

        if (usuarioRepository.findByEmail(registroUsuarioDTO.email()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }

        var userAuth = Usuario.builder()
                .usuarioUUID(UUID.randomUUID())
                .email(registroUsuarioDTO.email())
                .password(bPasswordEncoder.encode(registroUsuarioDTO.password()))
                .rol(com.femt.auth_service.data.model.Rol.valueOf(registroUsuarioDTO.rol()))
                .isEnabled(false)
                .build();

        usuarioRepository.save(userAuth);

        String token = tokenService.generateToken(userAuth);
        return new UsuarioResponseDTO(token, userAuth.getUsuarioUUID(), userAuth.getEmail(), userAuth.getRol().name());
    }

    public void setUserEnabled(UUID usuarioUUID) {
        Optional<Usuario> usuario = usuarioRepository.findByUsuarioUUID(usuarioUUID);
        if (usuario.isEmpty() || usuario.get().isEnabled()) {
            throw new NoSuchElementException("User not found");
        }
        var actualizarUsuario = usuario.get();
        actualizarUsuario.setEnabled(true);
        usuarioRepository.save(actualizarUsuario);
    }

    public List<UsuarioListResponseDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(u -> new UsuarioListResponseDTO(u.getUsuarioUUID(), u.getEmail(), u.getRol(), u.isEnabled()))
                .toList();
    }

    public UsuarioListResponseDTO findByUUID(UUID usuarioUUID) {
        Usuario usuario = usuarioRepository.findByUsuarioUUID(usuarioUUID)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        return new UsuarioListResponseDTO(usuario.getUsuarioUUID(), usuario.getEmail(), usuario.getRol(), usuario.isEnabled());
    }

    public UsuarioListResponseDTO update(UUID usuarioUUID, UpdateUsuarioDTO updateDTO) {
        Usuario usuario = usuarioRepository.findByUsuarioUUID(usuarioUUID)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        
        if (updateDTO.email() != null && !updateDTO.email().equals(usuario.getEmail())) {
            if (usuarioRepository.findByEmail(updateDTO.email()).isPresent()) {
                throw new IllegalArgumentException("Ya existe un usuario con ese email");
            }
            usuario.setEmail(updateDTO.email());
        }
        
        if (updateDTO.rol() != null) {
            usuario.setRol(updateDTO.rol());
        }
        
        if (updateDTO.isEnabled() != null) {
            usuario.setEnabled(updateDTO.isEnabled());
        }
        
        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return new UsuarioListResponseDTO(updatedUsuario.getUsuarioUUID(), updatedUsuario.getEmail(), 
                updatedUsuario.getRol(), updatedUsuario.isEnabled());
    }

    @Transactional
    public void delete(UUID usuarioUUID) {
        if (!usuarioRepository.findByUsuarioUUID(usuarioUUID).isPresent()) {
            throw new NoSuchElementException("Usuario no encontrado");
        }
        usuarioRepository.deleteByUsuarioUUID(usuarioUUID);
    }

}
