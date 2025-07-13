package com.femt.auth_service.data.dto.response;

import com.femt.auth_service.data.model.Rol;

public record ListarUsuarioResponseDTO(
        Long entidadId,
        String email,
        Rol rol) {
}