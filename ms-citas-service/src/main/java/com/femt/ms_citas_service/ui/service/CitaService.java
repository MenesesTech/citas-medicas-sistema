package com.femt.ms_citas_service.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.femt.ms_citas_service.data.dto.CitaRegistroDTO;
import com.femt.ms_citas_service.data.dto.CitaResponseDTO;
import com.femt.ms_citas_service.data.model.Cita;
import com.femt.ms_citas_service.data.repository.CitaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaService {
    @Autowired
    private CitaRepository citaRepository;

    public CitaResponseDTO guardar(CitaRegistroDTO registroDTO) {
        Cita cita = new Cita();
        cita.setFecha(registroDTO.fecha());
        cita.setHora(registroDTO.hora());
        cita.setIdMedico(registroDTO.idMedico());
        cita.setIdPaciente(registroDTO.idPaciente());
        try {
            citaRepository.save(cita);
            return new CitaResponseDTO(cita.getId(), cita.getFecha(), cita.getHora(), cita.getIdMedico(), cita.getIdPaciente());
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar la cita: " + e.getMessage());
        }
    }

    public List<CitaResponseDTO> listar() {
        return citaRepository.findAll().stream()
                .map(cita -> new CitaResponseDTO(cita.getId(), cita.getFecha(), cita.getHora(), cita.getIdMedico(), cita.getIdPaciente()))
                .collect(Collectors.toList());
    }

    public CitaResponseDTO obtener(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        return new CitaResponseDTO(cita.getId(), cita.getFecha(), cita.getHora(), cita.getIdMedico(), cita.getIdPaciente());
    }

    public CitaResponseDTO actualizar(Long id, CitaRegistroDTO registroDTO) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        cita.setFecha(registroDTO.fecha());
        cita.setHora(registroDTO.hora());
        cita.setIdMedico(registroDTO.idMedico());
        cita.setIdPaciente(registroDTO.idPaciente());
        citaRepository.save(cita);
        return new CitaResponseDTO(cita.getId(), cita.getFecha(), cita.getHora(), cita.getIdMedico(), cita.getIdPaciente());
    }

    public void eliminar(Long id) {
        if (!citaRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada");
        }
        citaRepository.deleteById(id);
    }
}
