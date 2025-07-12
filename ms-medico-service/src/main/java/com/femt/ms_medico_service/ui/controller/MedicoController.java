package com.femt.ms_medico_service.ui.controller;

import com.femt.ms_medico_service.data.model.Medico;
import com.femt.ms_medico_service.ui.service.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

    @GetMapping
    public ResponseEntity<List<Medico>> obtenerTodosMedicos() {
        List<Medico> medicos = medicoService.obtenerTodosMedicos();
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> obtenerMedicoPorId(@PathVariable UUID id) {
        Medico medico = medicoService.obtenerMedicoPorId(id);
        return ResponseEntity.ok(medico);
    }

    @PostMapping
    public ResponseEntity<Medico> crearMedico(@RequestBody Medico medico) {
        Medico nuevoMedico = medicoService.crearMedico(medico);
        return ResponseEntity.ok(nuevoMedico);
    }
}