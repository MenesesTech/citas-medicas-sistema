package com.femt.ms_patient_service.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.femt.ms_patient_service.data.model.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}