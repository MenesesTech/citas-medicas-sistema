package com.femt.ms_citas_service.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.femt.ms_citas_service.data.dto.RecetaRegistroDTO;
import com.femt.ms_citas_service.data.dto.RecetaResponseDTO;
import com.femt.ms_citas_service.data.model.Cita;
import com.femt.ms_citas_service.data.model.Receta;
import com.femt.ms_citas_service.data.repository.CitaRepository;
import com.femt.ms_citas_service.data.repository.RecetaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecetaService {
    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private CitaRepository citaRepository;

    public RecetaResponseDTO crearReceta(RecetaRegistroDTO dto) {
        Cita cita = citaRepository.findById(dto.idCita()).orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        Receta receta = new Receta();
        receta.setCita(cita);
        receta.setDiagnostico(dto.diagnostico());
        receta.setDosis(dto.dosis());
        receta.setMedicamento(dto.medicamento());
        try {
            recetaRepository.save(receta);
            return new RecetaResponseDTO(receta.getId(), receta.getDiagnostico(), receta.getMedicamento(),
                    receta.getDosis(), receta.getCita().getId());
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la receta: " + e.getMessage());
        }
    }

    public List<RecetaResponseDTO> listar() {
        return recetaRepository.findAll().stream()
                .map(receta -> new RecetaResponseDTO(receta.getId(), receta.getDiagnostico(), receta.getMedicamento(),
                        receta.getDosis(), receta.getCita().getId()))
                .collect(Collectors.toList());
    }

    public RecetaResponseDTO obtener(Long id) {
        Receta receta = recetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));
        return new RecetaResponseDTO(receta.getId(), receta.getDiagnostico(), receta.getMedicamento(),
                receta.getDosis(), receta.getCita().getId());
    }

    public RecetaResponseDTO actualizar(Long id, RecetaRegistroDTO dto) {
        Receta receta = recetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));
        Cita cita = citaRepository.findById(dto.idCita())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        receta.setCita(cita);
        receta.setDiagnostico(dto.diagnostico());
        receta.setMedicamento(dto.medicamento());
        receta.setDosis(dto.dosis());
        recetaRepository.save(receta);
        return new RecetaResponseDTO(receta.getId(), receta.getDiagnostico(), receta.getMedicamento(),
                receta.getDosis(), receta.getCita().getId());
    }

    public void eliminar(Long id) {
        if (!recetaRepository.existsById(id)) {
            throw new RuntimeException("Receta no encontrada");
        }
        recetaRepository.deleteById(id);
    }
}
