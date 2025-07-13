package com.femt.ms_citas_service.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.femt.ms_citas_service.data.model.Receta;

public interface RecetaRepository extends JpaRepository<Receta, Long> {

}
