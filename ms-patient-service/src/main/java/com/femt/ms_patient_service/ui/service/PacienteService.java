package com.femt.ms_patient_service.ui.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.femt.ms_patient_service.data.dto.request.RegistroPacienteDTO;
import com.femt.ms_patient_service.data.dto.request.UpdatePacienteDTO;
import com.femt.ms_patient_service.data.dto.response.PacienteResponseDTO;
import com.femt.ms_patient_service.data.model.Paciente;
import com.femt.ms_patient_service.data.repository.PacienteRepository;
import com.femt.ms_patient_service.security.TokenService;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TokenService tokenService;

    public PacienteResponseDTO registrar(String token, RegistroPacienteDTO registroPacienteDTO) {
        UUID userUUID = tokenService.extraerDatos(token).uuid();

        if (pacienteRepository.existsByUsuarioUUID(userUUID)) {
            throw new RuntimeException("El paciente ya está registrado");
        }

        Paciente paciente = new Paciente(
                userUUID,
                registroPacienteDTO.nombre(),
                registroPacienteDTO.apellidos(),
                registroPacienteDTO.dni(),
                registroPacienteDTO.ciudad(),
                registroPacienteDTO.distrito()
        );

        pacienteRepository.save(paciente);
        return new PacienteResponseDTO(
                paciente.getUsuarioUUID(),
                paciente.getNombre(),
                paciente.getApellidos(),
                paciente.getDni(),
                paciente.getCiudad(),
                paciente.getDistrito()
        );
    }

    public PacienteResponseDTO obtener(String token) {
        UUID userUUID = tokenService.extraerDatos(token).uuid();
        
        Paciente paciente = pacienteRepository.findByUsuarioUUID(userUUID)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        return new PacienteResponseDTO(
                paciente.getUsuarioUUID(),
                paciente.getNombre(),
                paciente.getApellidos(),
                paciente.getDni(),
                paciente.getCiudad(),
                paciente.getDistrito()
        );
    }

    public PacienteResponseDTO modificar(String token, UpdatePacienteDTO updatePacienteDTO) {
        UUID userUUID = tokenService.extraerDatos(token).uuid();
        
        Paciente paciente = pacienteRepository.findByUsuarioUUID(userUUID)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        paciente.setCiudad(updatePacienteDTO.ciudad());
        paciente.setDistrito(updatePacienteDTO.distrito());

        pacienteRepository.save(paciente);
        return new PacienteResponseDTO(
                paciente.getUsuarioUUID(),
                paciente.getNombre(),
                paciente.getApellidos(),
                paciente.getDni(),
                paciente.getCiudad(),
                paciente.getDistrito()
        );
    }

    public java.util.List<PacienteResponseDTO> listarTodos(String token) {
        var datosToken = tokenService.extraerDatos(token);
        
        if (!"ADMIN".equals(datosToken.role())) {
            throw new RuntimeException("Solo los administradores pueden listar pacientes");
        }
        
        return pacienteRepository.findAll().stream()
                .map(paciente -> new PacienteResponseDTO(
                        paciente.getUsuarioUUID(),
                        paciente.getNombre(),
                        paciente.getApellidos(),
                        paciente.getDni(),
                        paciente.getCiudad(),
                        paciente.getDistrito()
                ))
                .collect(java.util.stream.Collectors.toList());
    }

    public void eliminar(String token, UUID pacienteUUID) {
        var datosToken = tokenService.extraerDatos(token);
        
        if (!"ADMIN".equals(datosToken.role())) {
            throw new RuntimeException("Solo los administradores pueden eliminar pacientes");
        }
        
        Paciente paciente = pacienteRepository.findByUsuarioUUID(pacienteUUID)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        pacienteRepository.delete(paciente);
    }
}