package com.femt.medical.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.femt.medical.dto.MedicalStaffDTO;
import com.femt.medical.models.MedicalStaff;
import com.femt.medical.repository.MedicalStaffRepository;

@Service
public class MedicalStaffService {
    @Autowired
    private MedicalStaffRepository medicalStaffRepository;

    // Metodo para listar medico
    public List<MedicalStaffDTO> getAllMecicalStaff() {
        return medicalStaffRepository.findByActiveTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Metodod para convertir Entidad a DTO
    private MedicalStaffDTO convertToDTO(MedicalStaff medicalStaff) {
        return new MedicalStaffDTO(
                medicalStaff.getId(),
                medicalStaff.getUserId(),
                medicalStaff.getPhone(),
                medicalStaff.getSpecialty(),
                medicalStaff.getStartTime(),
                medicalStaff.getEndTime(),
                medicalStaff.getWorkingDays(),
                medicalStaff.isActive());
    }
}
