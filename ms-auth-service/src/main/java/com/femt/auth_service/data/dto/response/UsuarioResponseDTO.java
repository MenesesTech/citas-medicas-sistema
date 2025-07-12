package com.femt.auth_service.data.dto.response;

import java.util.UUID;

public record UsuarioResponseDTO(String token, UUID usuarioUUID, String email, String rol) {
}
