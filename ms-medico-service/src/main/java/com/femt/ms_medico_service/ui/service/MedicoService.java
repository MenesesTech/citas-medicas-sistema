package com.femt.ms_medico_service.ui.service;

import com.femt.ms_medico_service.data.model.Medico;
import com.femt.ms_medico_service.data.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicoService {
    private final MedicoRepository medicoRepository;
    private final RestTemplate restTemplate;

    @Value("${ms-citas-service.url:http://ms-citas-service/api/citas/sync}")
    private String citaServiceUrl;

    public List<Medico> obtenerTodosMedicos() {
        return medicoRepository.findAll();
    }

    public Medico obtenerMedicoPorId(UUID id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
    }

    public Medico crearMedico(Medico medico) {
        Medico savedMedico = medicoRepository.save(medico);
        // Sincronizar con ms-citas-service
        syncMedicoWithCitasService(savedMedico);
        return savedMedico;
    }

    private void syncMedicoWithCitasService(Medico medico) {
        try {
            restTemplate.postForEntity(citaServiceUrl + "/medico", medico, Medico.class);
        } catch (Exception e) {
            // Loggear el error, pero no fallar la creación del médico
            System.err.println("Error al sincronizar médico con ms-citas-service: " + e.getMessage());
        }
    }
}