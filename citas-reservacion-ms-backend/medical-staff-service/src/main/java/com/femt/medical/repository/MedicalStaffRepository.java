package com.femt.medical.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.femt.medical.models.MedicalStaff;
import com.femt.medical.models.Specialty;

@Repository
public interface MedicalStaffRepository extends JpaRepository<MedicalStaff, Long> {

    Optional<MedicalStaff> findBySpecialty(Specialty specialty);

    List<MedicalStaff> findByActiveTrue();

    @Query("SELECT m FROM MedicalStaff m WHERE m.specialty = :specialty AND m.active = true")
    List<MedicalStaff> findActiveBySpecialty(@Param("specialty") Specialty specialty);

    @Query("SELECT m FROM MedicalStaff m WHERE m.name LIKE %:name% AND m.active = true")
    List<MedicalStaff> findByNameContainingAndActiveTrue(@Param("name") String name);
}
