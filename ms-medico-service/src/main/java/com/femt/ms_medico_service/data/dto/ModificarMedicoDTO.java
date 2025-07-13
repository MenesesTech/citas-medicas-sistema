package com.femt.ms_medico_service.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModificarMedicoDTO(
        @NotNull Long id,
        @NotBlank String nombreCompleto,
        @NotBlank String especialidad,
        @NotBlank String centroMedico) {

}
