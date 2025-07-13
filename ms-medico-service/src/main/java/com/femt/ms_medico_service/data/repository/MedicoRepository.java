package com.femt.ms_medico_service.data.repository;

import com.femt.ms_medico_service.data.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
}