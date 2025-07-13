package com.femt.ms_patient_service.ui.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.femt.ms_patient_service.data.dto.request.RegistroPacienteDTO;
import com.femt.ms_patient_service.data.dto.request.UpdatePacienteDTO;
import com.femt.ms_patient_service.data.dto.response.PacienteResponseDTO;
import com.femt.ms_patient_service.data.model.Paciente;
import com.femt.ms_patient_service.ui.service.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/pacientes", produces = { "application/json" })
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping(value = "/registrar", consumes = { "application/json" })
    public ResponseEntity<PacienteResponseDTO> registrarPaciente(
            @Valid @RequestBody RegistroPacienteDTO registroPacienteDTO) {
        try {
            PacienteResponseDTO response = pacienteService.guardarPaciente(registroPacienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {
        List<Paciente> pacientes = pacienteService.listarTodos();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        return paciente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}", consumes = { "application/json" })
    public ResponseEntity<Paciente> modificarPaciente(@PathVariable Long id,
            @Valid @RequestBody UpdatePacienteDTO updateDTO) {
        try {
            Paciente pacienteModificado = pacienteService.modificarPaciente(id, updateDTO);
            return ResponseEntity.ok(pacienteModificado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id) {
        try {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}