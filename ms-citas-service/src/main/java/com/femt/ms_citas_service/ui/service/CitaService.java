package com.femt.ms_citas_service.ui.service;

import com.femt.ms_citas_service.data.dto.CitaRequest;
import com.femt.ms_citas_service.data.model.Cita;
import com.femt.ms_citas_service.data.model.Medico;
import com.femt.ms_citas_service.data.model.Paciente;
import com.femt.ms_citas_service.data.repository.CitaRepository;
import com.femt.ms_citas_service.data.repository.MedicoRepository;
import com.femt.ms_citas_service.data.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CitaService {
    private final CitaRepository citaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public Cita reservarCita(CitaRequest request, String pacienteUUID) {
        UUID pacienteId = UUID.fromString(pacienteUUID);

        Paciente paciente = pacienteRepository.findById(pacienteId)
            .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        Medico medico = medicoRepository.findById(request.getIdMedico())
            .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        Cita cita = new Cita();
        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());
        cita.setMedico(medico);
        cita.setPaciente(paciente);

        return citaRepository.save(cita);
    }

    public List<Cita> obtenerCitasDelMedico(String medicoUUID) {
        UUID id = UUID.fromString(medicoUUID);
        return citaRepository.findByMedicoId(id);
    }

    public List<Cita> obtenerCitasDelPaciente(String pacienteUUID) {
        UUID id = UUID.fromString(pacienteUUID);
        return citaRepository.findByPacienteId(id);
    }
}