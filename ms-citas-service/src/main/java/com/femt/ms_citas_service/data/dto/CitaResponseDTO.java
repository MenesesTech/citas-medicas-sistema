package com.femt.ms_citas_service.data.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CitaResponseDTO(Long id, LocalDate fecha, LocalTime hora, Long idMedico, Long idPaciente) {

}
