package com.femt.ms_citas_service.data.repository;

import com.femt.ms_citas_service.data.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
}