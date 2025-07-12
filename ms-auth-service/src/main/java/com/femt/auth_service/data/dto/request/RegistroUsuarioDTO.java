package com.femt.auth_service.data.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegistroUsuarioDTO(
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    String email,
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    String password,
    
    @NotBlank(message = "El rol es obligatorio")
    @Pattern(regexp = "ADMIN|DOCTOR|PATIENT", message = "El rol debe ser ADMIN, DOCTOR o PATIENT")
    String rol
) {
}