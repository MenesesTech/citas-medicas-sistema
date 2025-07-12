package com.femt.auth_service.data.dto.response;

import java.util.UUID;

import com.femt.auth_service.data.model.Rol;

public record UsuarioListResponseDTO(
    UUID usuarioUUID,
    String email,
    Rol rol,
    boolean isEnabled
) {}