package com.femt.ms_citas_service.ui.controller;

import com.femt.ms_citas_service.data.dto.CitaRequest;
import com.femt.ms_citas_service.data.model.Cita;
import com.femt.ms_citas_service.ui.service.CitaService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @PostMapping("/reservar")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> reservarCita(@RequestBody CitaRequest request,
            @RequestHeader("Authorization") String token) {
        // Extraer UUID desde el token
        String cleanedToken = token.replace("Bearer ", "");
        UUID pacienteId = citaService.extraerPacienteIdDesdeToken(cleanedToken);
        Cita cita = citaService.reservarCita(request, pacienteId);
        return ResponseEntity.ok(cita);
    }

    @GetMapping("/medico")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<List<Cita>> obtenerCitasDelMedico(@RequestHeader("Authorization") String token) {
        String cleanedToken = token.replace("Bearer ", "");
        UUID medicoId = citaService.extraerPacienteIdDesdeToken(cleanedToken);
        List<Cita> citas = citaService.obtenerCitasDelMedico(medicoId);
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/paciente")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<Cita>> obtenerCitasDelPaciente(@RequestHeader("Authorization") String token) {
        String cleanedToken = token.replace("Bearer ", "");
        UUID pacienteId = citaService.extraerPacienteIdDesdeToken(cleanedToken);
        List<Cita> citas = citaService.obtenerCitasDelPaciente(pacienteId);
        return ResponseEntity.ok(citas);
    }
}
