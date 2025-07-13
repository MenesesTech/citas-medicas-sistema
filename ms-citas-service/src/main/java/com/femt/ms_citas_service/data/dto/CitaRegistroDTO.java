package com.femt.ms_citas_service.data.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CitaRegistroDTO(LocalDate fecha, LocalTime hora, Long idMedico, Long idPaciente) {
}