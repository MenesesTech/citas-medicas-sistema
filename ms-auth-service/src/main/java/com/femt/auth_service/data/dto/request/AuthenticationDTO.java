package com.femt.auth_service.data.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
                @NotBlank(message = "El email es obligatorio") @Email(message = "El email debe tener un formato válido") String email,

                @NotBlank(message = "La contraseña es obligatoria") String password) {
}
