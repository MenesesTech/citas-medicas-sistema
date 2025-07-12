package com.femt.ms_citas_service.ui.controller;

import com.femt.ms_citas_service.data.model.Medico;
import com.femt.ms_citas_service.data.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/citas/sync")
@RequiredArgsConstructor
public class CitaSyncController {

    private final MedicoRepository medicoRepository;

    @PostMapping("/medico")
    public ResponseEntity<Medico> syncMedico(@RequestBody Medico medico) {
        Medico savedMedico = medicoRepository.save(medico);
        return ResponseEntity.ok(savedMedico);
    }
}