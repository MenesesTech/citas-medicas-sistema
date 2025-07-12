package com.femt.ms_medico_service.ui.service;

import com.femt.ms_medico_service.data.model.Medico;
import com.femt.ms_medico_service.data.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicoService {
    private final MedicoRepository medicoRepository;

    public List<Medico> obtenerTodosMedicos() {
        return medicoRepository.findAll();
    }

    public Medico obtenerMedicoPorId(UUID id) {
        return medicoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
    }

    public Medico crearMedico(Medico medico) {
        return medicoRepository.save(medico);
    }
}