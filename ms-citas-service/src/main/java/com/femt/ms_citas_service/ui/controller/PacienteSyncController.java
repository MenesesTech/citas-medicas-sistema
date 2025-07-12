package com.femt.ms_citas_service.ui.controller;

import com.femt.ms_citas_service.data.model.Paciente;
import com.femt.ms_citas_service.data.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/citas/sync")
@RequiredArgsConstructor
public class PacienteSyncController {

    private final PacienteRepository pacienteRepository;

    @PostMapping("/paciente")
    public ResponseEntity<Paciente> syncPaciente(@RequestBody Paciente paciente) {
        Paciente savedPaciente = pacienteRepository.save(paciente);
        return ResponseEntity.ok(savedPaciente);
    }
}