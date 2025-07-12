package com.femt.ms_patient_service.ui.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.femt.ms_patient_service.data.dto.request.RegistroPacienteDTO;
import com.femt.ms_patient_service.data.dto.request.UpdatePacienteDTO;
import com.femt.ms_patient_service.data.dto.response.PacienteResponseDTO;
import com.femt.ms_patient_service.ui.service.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/pacientes", produces = { "application/json" })
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping(value = "/registrar", consumes = { "application/json" })
    public ResponseEntity<PacienteResponseDTO> registrarPaciente(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody RegistroPacienteDTO registroPacienteDTO) {

        String token = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pacienteService.registrar(token, registroPacienteDTO));
    }

    @GetMapping
    public ResponseEntity<PacienteResponseDTO> obtenerPaciente(
            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(pacienteService.obtener(token));
    }

    @PutMapping(value = "/modificar", consumes = { "application/json" })
    public ResponseEntity<PacienteResponseDTO> modificarPaciente(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody UpdatePacienteDTO updatePacienteDTO) {

        String token = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(pacienteService.modificar(token, updatePacienteDTO));
    }

    @GetMapping("/admin/listar")
    public ResponseEntity<java.util.List<PacienteResponseDTO>> listarPacientes(
            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(pacienteService.listarTodos(token));
    }

    @DeleteMapping("/{usuarioUUID}")
    public ResponseEntity<Void> eliminarPaciente(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable UUID usuarioUUID) {

        String token = authorizationHeader.replace("Bearer ", "");
        pacienteService.eliminar(token, usuarioUUID);
        return ResponseEntity.noContent().build();
    }
}