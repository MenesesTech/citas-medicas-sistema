package com.femt.ms_citas_service.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.femt.ms_citas_service.data.dto.RecetaRegistroDTO;
import com.femt.ms_citas_service.data.dto.RecetaResponseDTO;
import com.femt.ms_citas_service.ui.service.RecetaService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/recetas", produces = { "application/json" })
public class RecetaController {
    @Autowired
    private RecetaService recetaService;

    @PostMapping(consumes = { "application/json" })
    public ResponseEntity<RecetaResponseDTO> crear(@Valid @RequestBody RecetaRegistroDTO recetaDTO) {
        try {
            RecetaResponseDTO response = recetaService.crearReceta(recetaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<RecetaResponseDTO>> listar() {
        List<RecetaResponseDTO> recetas = recetaService.listar();
        return ResponseEntity.ok(recetas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaResponseDTO> obtener(@PathVariable Long id) {
        try {
            RecetaResponseDTO receta = recetaService.obtener(id);
            return ResponseEntity.ok(receta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping(value = "/{id}", consumes = { "application/json" })
    public ResponseEntity<RecetaResponseDTO> actualizar(
            @PathVariable Long id, @Valid @RequestBody RecetaRegistroDTO recetaDTO) {
        try {
            RecetaResponseDTO response = recetaService.actualizar(id, recetaDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            recetaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}