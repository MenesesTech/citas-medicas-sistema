package com.femt.auth_service.data.dto.request;

import com.femt.auth_service.data.model.Rol;

import jakarta.validation.constraints.Email;

public record UpdateUsuarioDTO(
    @Email(message = "Email debe tener un formato válido")
    String email,
    Rol rol,
    Boolean isEnabled
) {}