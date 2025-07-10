package com.femt.medical.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.femt.medical.clients.UserClient;
import com.femt.medical.clients.UserResponse;
import com.femt.medical.dto.CreateMedicalStaffDTO;
import com.femt.medical.dto.MedicalStaffDTO;
import com.femt.medical.dto.UpdateMedicalStaffDTO;
import com.femt.medical.exceptions.ResourceNotFoundException;
import com.femt.medical.exceptions.UnauthorizedException;
import com.femt.medical.models.MedicalStaff;
import com.femt.medical.models.Specialty;
import com.femt.medical.repository.MedicalStaffRepository;

@Service
public class MedicalStaffService {
    @Autowired
    private MedicalStaffRepository medicalStaffRepository;

    @Autowired
    private UserClient userClient;

    // Método para listar todos los médicos activos
    public List<MedicalStaffDTO> getAllMedicalStaff() {
        return medicalStaffRepository.findByActiveTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Método para obtener médico por ID
    public MedicalStaffDTO getMedicalStaffById(Long id) {
        MedicalStaff staff = medicalStaffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical staff not found with id: " + id));
        return convertToDTO(staff);
    }

    // Método para obtener médicos por especialidad
    public List<MedicalStaffDTO> getMedicalStaffBySpecialty(Specialty specialty) {
        return medicalStaffRepository.findActiveBySpecialty(specialty)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Método para obtener médicos disponibles
    public List<MedicalStaffDTO> getAvailableMedicalStaff() {
        return medicalStaffRepository.findByActiveTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Método para crear un nuevo médico
    public MedicalStaffDTO createMedicalStaff(CreateMedicalStaffDTO createDTO, String token) {
        // Validar que el usuario existe y tiene permisos
        UserResponse userResponse = userClient.getUserProfile(token);

        // Verificar que el usuario tiene rol de DOCTOR o ADMIN
        if (!userResponse.rol().equals("DOCTOR") && !userResponse.rol().equals("ADMIN")) {
            throw new UnauthorizedException("User does not have permission to create medical staff");
        }

        // Crear nueva instancia de MedicalStaff
        MedicalStaff medicalStaff = new MedicalStaff();
        medicalStaff.setUserId(createDTO.userId());
        medicalStaff.setPhone(createDTO.phone());
        medicalStaff.setSpecialty(Specialty.valueOf(createDTO.specialty()));
        medicalStaff.setStartTime(createDTO.starTime());
        medicalStaff.setEndTime(createDTO.endTime());
        medicalStaff.setWorkingDays(createDTO.workingDays());
        medicalStaff.setActive(true);

        MedicalStaff savedStaff = medicalStaffRepository.save(medicalStaff);
        return convertToDTO(savedStaff);
    }

    // Método para actualizar médico
    public MedicalStaffDTO updateMedicalStaff(Long id, UpdateMedicalStaffDTO updateDTO, String token) {
        // Validar que el usuario existe y tiene permisos
        UserResponse userResponse = userClient.getUserProfile(token);

        MedicalStaff existingStaff = medicalStaffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical staff not found with id: " + id));

        // Verificar que el usuario tiene permisos (es el mismo médico o es admin)
        if (!userResponse.rol().equals("ADMIN") && !existingStaff.getUserId().equals(userResponse.id())) {
            throw new UnauthorizedException("User does not have permission to update this medical staff");
        }

        // Actualizar campos
        if (updateDTO.phone() != null) {
            existingStaff.setPhone(updateDTO.phone());
        }
        if (updateDTO.specialty() != null) {
            existingStaff.setSpecialty(updateDTO.specialty());
        }
        if (updateDTO.startTime() != null) {
            existingStaff.setStartTime(updateDTO.startTime());
        }
        if (updateDTO.endTime() != null) {
            existingStaff.setEndTime(updateDTO.endTime());
        }
        if (updateDTO.workingDays() != null) {
            existingStaff.setWorkingDays(updateDTO.workingDays());
        }

        MedicalStaff updatedStaff = medicalStaffRepository.save(existingStaff);
        return convertToDTO(updatedStaff);
    }

    // Método para desactivar médico
    public void deactivateMedicalStaff(Long id, String token) {
        UserResponse userResponse = userClient.getUserProfile(token);

        MedicalStaff staff = medicalStaffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical staff not found with id: " + id));

        // Solo admin puede desactivar médicos
        if (!userResponse.rol().equals("ADMIN")) {
            throw new UnauthorizedException("Only admin can deactivate medical staff");
        }

        staff.setActive(false);
        medicalStaffRepository.save(staff);
    }

    // Método para activar médico
    public MedicalStaffDTO activateMedicalStaff(Long id, String token) {
        UserResponse userResponse = userClient.getUserProfile(token);

        MedicalStaff staff = medicalStaffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical staff not found with id: " + id));

        // Solo admin puede activar médicos
        if (!userResponse.rol().equals("ADMIN")) {
            throw new UnauthorizedException("Only admin can activate medical staff");
        }

        staff.setActive(true);
        MedicalStaff updatedStaff = medicalStaffRepository.save(staff);
        return convertToDTO(updatedStaff);
    }

    // Método para convertir Entidad a DTO
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