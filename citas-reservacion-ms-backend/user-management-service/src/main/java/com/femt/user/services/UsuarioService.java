package com.femt.user.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.femt.user.dto.UserProfileDTO;
import com.femt.user.exceptions.ResourceNotFoundException;
import com.femt.user.models.Usuario;
import com.femt.user.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = (Usuario) userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }
        return user;
    }

    public Usuario getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    public List<UserProfileDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserProfileDTO(
                        user.getId(),
                        user.getName(),
                        user.getDni(),
                        user.getEmail(),
                        user.getRol()))
                .collect(Collectors.toList());
    }
}
