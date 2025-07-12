package com.femt.ms_patient_service.data.dto.response;

import java.util.UUID;

public record PacienteResponseDTO(UUID usuarioUUID, String nombre, String apellidos, String dni, String ciudad, String distrito) {
}
