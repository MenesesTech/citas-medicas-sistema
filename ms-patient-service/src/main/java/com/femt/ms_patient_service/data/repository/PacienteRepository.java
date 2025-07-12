package com.femt.ms_patient_service.data.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.femt.ms_patient_service.data.model.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByUsuarioUUID(UUID usuarioUUID);
    boolean existsByUsuarioUUID(UUID usuarioUUID);
}