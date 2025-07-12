package com.femt.ms_citas_service.data.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class CitaRequest {
    private UUID idMedico;
    private LocalDate fecha;
    private LocalTime hora;
}