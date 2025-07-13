package com.femt.ms_medico_service.ui.controller;

import com.femt.ms_medico_service.data.dto.ListarMedicoDTO;
import com.femt.ms_medico_service.data.dto.ModificarMedicoDTO;
import com.femt.ms_medico_service.data.dto.RegistroMedicoDTO;
import com.femt.ms_medico_service.data.dto.RegistroResponseDTO;
import com.femt.ms_medico_service.ui.service.MedicoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/medicos", produces = { "application/json" })
public class MedicoController {
    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping(value = "/registro", consumes = { "application/json" })
    public ResponseEntity<RegistroResponseDTO> registro(
            @Valid @RequestBody RegistroMedicoDTO registroMedicoDTO) {
        try {
            RegistroResponseDTO response = medicoService.guardar(registroMedicoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ListarMedicoDTO>> listarTodos() {
        List<ListarMedicoDTO> medicos = medicoService.listarTodos();
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListarMedicoDTO> obtenerPorId(@PathVariable Long id) {
        return medicoService.obtenerPorId(id)
                .map(medico -> ResponseEntity.ok(medico))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(consumes = { "application/json" })
    public ResponseEntity<ListarMedicoDTO> modificar(
            @Valid @RequestBody ModificarMedicoDTO modificarMedicoDTO) {
        return medicoService.modificar(modificarMedicoDTO)
                .map(medico -> ResponseEntity.ok(medico))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (medicoService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}