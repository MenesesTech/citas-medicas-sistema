package com.femt.medical.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.femt.medical.dto.CreateMedicalStaffDTO;
import com.femt.medical.dto.MedicalStaffDTO;
import com.femt.medical.dto.UpdateMedicalStaffDTO;
import com.femt.medical.models.Specialty;
import com.femt.medical.services.MedicalStaffService;

@RestController
@RequestMapping("/api/medical-staff")
public class MedicalStaffController {

    @Autowired
    private MedicalStaffService medicalStaffService;

    @GetMapping
    public ResponseEntity<List<MedicalStaffDTO>> getAllMedicalStaff() {
        List<MedicalStaffDTO> staff = medicalStaffService.getAllMedicalStaff();
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalStaffDTO> getMedicalStaffById(@PathVariable Long id) {
        MedicalStaffDTO staff = medicalStaffService.getMedicalStaffById(id);
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<List<MedicalStaffDTO>> getMedicalStaffBySpecialty(@PathVariable Specialty specialty) {
        List<MedicalStaffDTO> staff = medicalStaffService.getMedicalStaffBySpecialty(specialty);
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/available")
    public ResponseEntity<List<MedicalStaffDTO>> getAvailableMedicalStaff() {
        List<MedicalStaffDTO> staff = medicalStaffService.getAvailableMedicalStaff();
        return ResponseEntity.ok(staff);
    }

    @PostMapping
    public ResponseEntity<MedicalStaffDTO> createMedicalStaff(
            @RequestBody CreateMedicalStaffDTO createDTO,
            @RequestHeader("Authorization") String token) {
        MedicalStaffDTO createdStaff = medicalStaffService.createMedicalStaff(createDTO, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStaff);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalStaffDTO> updateMedicalStaff(
            @PathVariable Long id,
            @RequestBody UpdateMedicalStaffDTO updateDTO,
            @RequestHeader("Authorization") String token) {
        MedicalStaffDTO updatedStaff = medicalStaffService.updateMedicalStaff(id, updateDTO, token);
        return ResponseEntity.ok(updatedStaff);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalStaff(@PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        medicalStaffService.deactivateMedicalStaff(id, token);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<MedicalStaffDTO> activateMedicalStaff(@PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        MedicalStaffDTO staff = medicalStaffService.activateMedicalStaff(id, token);
        return ResponseEntity.ok(staff);
    }
}