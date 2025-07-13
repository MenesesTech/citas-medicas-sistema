package com.femt.auth_service.ui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.femt.auth_service.data.dto.request.RegistroUsuarioDTO;
import com.femt.auth_service.data.dto.request.UpdateUsuarioDTO;
import com.femt.auth_service.data.dto.response.ListarUsuarioResponseDTO;
import com.femt.auth_service.data.dto.response.LoginResponseDTO;
import com.femt.auth_service.data.dto.response.RegistroResponseDTO;
import com.femt.auth_service.data.model.Usuario;
import com.femt.auth_service.data.repository.UsuarioRepository;
import com.femt.auth_service.security.TokenService;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder BCryptPasswordEncoder;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.usuarioRepository.findByEmail(email).orElseThrow();
    }

    public LoginResponseDTO login(String email, String password) {
        var user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        boolean passwordValido = BCryptPasswordEncoder.matches(password, user.getPassword());

        if (!passwordValido) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = tokenService.generateToken(user);

        return new LoginResponseDTO(token);
    }

    public RegistroResponseDTO registro(RegistroUsuarioDTO registroUsuarioDTO) {
        if (usuarioRepository.findByEmail(registroUsuarioDTO.email()).isPresent() ||
                usuarioRepository.findByEntidadId(registroUsuarioDTO.entidadId()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }
        Usuario usuario = new Usuario(registroUsuarioDTO.email(),
                BCryptPasswordEncoder.encode(registroUsuarioDTO.password()),
                registroUsuarioDTO.rol(), registroUsuarioDTO.entidadId());
        try {
            usuarioRepository.save(usuario);
            String token = tokenService.generateToken(usuario);
            return new RegistroResponseDTO(registroUsuarioDTO.entidadId(), token);
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar un usuario");
        }
    }

    @Transactional
    public void actualizarEmailPassword(Long entidadId, UpdateUsuarioDTO data) {
        Usuario usuario = usuarioRepository.findByEntidadId(entidadId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setEmail(data.email());
        usuario.setPassword(BCryptPasswordEncoder.encode(data.password()));

        usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long entidadId) {
        Usuario usuario = usuarioRepository.findByEntidadId(entidadId)
                .orElseThrow(() -> new RuntimeException("Usuario no encotrado"));
        try {
            usuarioRepository.delete(usuario);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar usuario");
        }
    }

    public List<ListarUsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> new ListarUsuarioResponseDTO(usuario.getEntidadId(), usuario.getEmail(),
                        usuario.getRol()))
                .toList();
    }

}
