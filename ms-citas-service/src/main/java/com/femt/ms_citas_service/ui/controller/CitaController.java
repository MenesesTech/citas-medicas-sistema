package com.femt.ms_citas_service.ui.controller;

import com.femt.ms_citas_service.data.dto.CitaRequest;
import com.femt.ms_citas_service.data.model.Cita;
import com.femt.ms_citas_service.ui.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @PostMapping("/reservar")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Cita> reservarCita(
            @RequestBody CitaRequest request,
            @AuthenticationPrincipal Jwt principal) {

        String pacienteUUID = principal.getSubject();
        Cita cita = citaService.reservarCita(request, pacienteUUID);
        return ResponseEntity.ok(cita);
    }

    @GetMapping("/medico")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<List<Cita>> obtenerCitasDelMedico(@AuthenticationPrincipal Jwt principal) {
        String medicoUUID = principal.getSubject();
        List<Cita> citas = citaService.obtenerCitasDelMedico(medicoUUID);
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/paciente")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<Cita>> obtenerCitasDelPaciente(@AuthenticationPrincipal Jwt principal) {
        String pacienteUUID = principal.getSubject();
        List<Cita> citas = citaService.obtenerCitasDelPaciente(pacienteUUID);
        return ResponseEntity.ok(citas);
    }
}