package com.femt.ms_citas_service.ui.service;

import com.femt.ms_citas_service.data.dto.CitaRequest;
import com.femt.ms_citas_service.data.model.Cita;
import com.femt.ms_citas_service.data.model.Medico;
import com.femt.ms_citas_service.data.model.Paciente;
import com.femt.ms_citas_service.data.repository.CitaRepository;
import com.femt.ms_citas_service.data.repository.MedicoRepository;
import com.femt.ms_citas_service.data.repository.PacienteRepository;
import com.femt.ms_citas_service.exception.MedicoNotFoundException;
import com.femt.ms_citas_service.exception.PacienteNotFoundException;
import com.femt.ms_citas_service.security.TokenService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CitaService {
    private final CitaRepository citaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    @Autowired
    private TokenService tokenService;

    @Transactional
    public Cita reservarCita(CitaRequest request, UUID pacienteId) {
        // Validar fecha y hora
        if (request.getFecha().isBefore(LocalDate.now()) ||
                (request.getFecha().isEqual(LocalDate.now()) && request.getHora().isBefore(LocalTime.now()))) {
            throw new IllegalArgumentException("La cita debe ser en una fecha y hora futura");
        }

        // Verificar disponibilidad del médico
        List<Cita> existingCitas = citaRepository.findByMedicoIdAndFechaAndHora(
                request.getIdMedico(), request.getFecha(), request.getHora());
        if (!existingCitas.isEmpty()) {
            throw new IllegalStateException("El médico ya tiene una cita en ese horario");
        }

        // Buscar paciente
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNotFoundException("Paciente no encontrado con ID: " + pacienteId));

        // Buscar médico
        Medico medico = medicoRepository.findById(request.getIdMedico())
                .orElseThrow(
                        () -> new MedicoNotFoundException("Médico no encontrado con ID: " + request.getIdMedico()));

        // Crear cita
        Cita cita = new Cita();
        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());
        cita.setMedico(medico);
        cita.setPaciente(paciente);

        return citaRepository.save(cita);
    }

    public List<Cita> obtenerCitasDelMedico(UUID medicoId) {
        return citaRepository.findByMedicoId(medicoId);
    }

    public List<Cita> obtenerCitasDelPaciente(UUID pacienteId) {
        return citaRepository.findByPacienteId(pacienteId);
    }

    public UUID extraerUsuarioIdDesdeToken(String token) {
        return tokenService.extraerDatos(token).uuid();
    }
}