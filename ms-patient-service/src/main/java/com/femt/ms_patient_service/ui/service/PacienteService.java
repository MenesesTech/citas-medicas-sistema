package com.femt.ms_patient_service.ui.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.femt.ms_patient_service.data.dto.request.RegistroPacienteDTO;
import com.femt.ms_patient_service.data.dto.request.UpdatePacienteDTO;
import com.femt.ms_patient_service.data.dto.response.PacienteResponseDTO;
import com.femt.ms_patient_service.data.model.Paciente;
import com.femt.ms_patient_service.data.repository.PacienteRepository;

import jakarta.transaction.Transactional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    public PacienteResponseDTO guardarPaciente(RegistroPacienteDTO registroPacienteDTO) {
        Paciente paciente = new Paciente();
        paciente.setNombre(registroPacienteDTO.nombre());
        paciente.setApellidos(registroPacienteDTO.apellidos());
        paciente.setDni(registroPacienteDTO.dni());
        paciente.setDireccion(registroPacienteDTO.direccion());
        paciente.setDistrito(registroPacienteDTO.distrito());

        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        return new PacienteResponseDTO(pacienteGuardado.getId());
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    @Transactional
    public Paciente modificarPaciente(Long id, UpdatePacienteDTO updateDTO) {
        Paciente paciente = pacienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        
        paciente.setNombre(updateDTO.nombre());
        paciente.setApellidos(updateDTO.apellidos());
        paciente.setDni(updateDTO.dni());
        paciente.setDireccion(updateDTO.direccion());
        paciente.setDistrito(updateDTO.distrito());
        
        return pacienteRepository.save(paciente);
    }

    @Transactional
    public void eliminarPaciente(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RuntimeException("Paciente no encontrado");
        }
        pacienteRepository.deleteById(id);
    }
}