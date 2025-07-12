package com.femt.ms_citas_service.data.repository;

import com.femt.ms_citas_service.data.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface CitaRepository extends JpaRepository<Cita, UUID> {
    List<Cita> findByMedicoId(UUID medicoId);

    List<Cita> findByPacienteId(UUID pacienteId);

    List<Cita> findByMedicoIdAndFechaAndHora(UUID medicoId, LocalDate fecha, LocalTime hora);
}