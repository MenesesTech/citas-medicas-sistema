package com.femt.ms_citas_service.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.femt.ms_citas_service.data.dto.CitaRegistroDTO;
import com.femt.ms_citas_service.data.dto.CitaResponseDTO;
import com.femt.ms_citas_service.ui.service.CitaService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/citas", produces = { "application/json" })
public class CitaController {
    @Autowired
    private CitaService citaService;

    @PostMapping(value = "/registro", consumes = { "application/json" })
    public ResponseEntity<CitaResponseDTO> registro(
            @Valid @RequestBody CitaRegistroDTO registroCitaDTO) {
        try {
            CitaResponseDTO response = citaService.guardar(registroCitaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> listar() {
        List<CitaResponseDTO> citas = citaService.listar();
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> obtener(@PathVariable Long id) {
        try {
            CitaResponseDTO cita = citaService.obtener(id);
            return ResponseEntity.ok(cita);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping(value = "/{id}", consumes = { "application/json" })
    public ResponseEntity<CitaResponseDTO> actualizar(
            @PathVariable Long id, @Valid @RequestBody CitaRegistroDTO citaDTO) {
        try {
            CitaResponseDTO response = citaService.actualizar(id, citaDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            citaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
