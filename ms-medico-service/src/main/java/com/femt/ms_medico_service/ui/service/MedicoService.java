package com.femt.ms_medico_service.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.femt.ms_medico_service.data.dto.ListarMedicoDTO;
import com.femt.ms_medico_service.data.dto.ModificarMedicoDTO;
import com.femt.ms_medico_service.data.dto.RegistroMedicoDTO;
import com.femt.ms_medico_service.data.dto.RegistroResponseDTO;
import com.femt.ms_medico_service.data.model.Medico;
import com.femt.ms_medico_service.data.repository.MedicoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    public RegistroResponseDTO guardar(RegistroMedicoDTO registroMedicoDTO) {
        Medico medico = new Medico();
        medico.setNombreCompleto(registroMedicoDTO.nombreCompleto());
        medico.setEspecialidad(registroMedicoDTO.especialidad());
        medico.setCentroMedico(registroMedicoDTO.centroMedico());
        try {
            medicoRepository.save(medico);
            return new RegistroResponseDTO(medico.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el médico en la base de datos");
        }
    }

    public List<ListarMedicoDTO> listarTodos() {
        return medicoRepository.findAll().stream()
                .map(medico -> new ListarMedicoDTO(
                        medico.getId(),
                        medico.getNombreCompleto(),
                        medico.getEspecialidad(),
                        medico.getCentroMedico()))
                .toList();
    }

    public Optional<ListarMedicoDTO> obtenerPorId(Long id) {
        return medicoRepository.findById(id)
                .map(medico -> new ListarMedicoDTO(
                        medico.getId(),
                        medico.getNombreCompleto(),
                        medico.getEspecialidad(),
                        medico.getCentroMedico()));
    }

    public Optional<ListarMedicoDTO> modificar(ModificarMedicoDTO modificarMedicoDTO) {
        return medicoRepository.findById(modificarMedicoDTO.id())
                .map(medico -> {
                    medico.setNombreCompleto(modificarMedicoDTO.nombreCompleto());
                    medico.setEspecialidad(modificarMedicoDTO.especialidad());
                    medico.setCentroMedico(modificarMedicoDTO.centroMedico());
                    Medico medicoGuardado = medicoRepository.save(medico);
                    return new ListarMedicoDTO(
                            medicoGuardado.getId(),
                            medicoGuardado.getNombreCompleto(),
                            medicoGuardado.getEspecialidad(),
                            medicoGuardado.getCentroMedico());
                });
    }

    public boolean eliminar(Long id) {
        if (medicoRepository.existsById(id)) {
            medicoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}