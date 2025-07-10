package com.femt.medical.dto;

import java.time.LocalTime;
import java.util.List;

import com.femt.medical.models.MedicalStaff.DayOfWeek;

public record CreateMedicalStaffDTO(Long userId, String phone, String specialty,
        LocalTime starTime, LocalTime endTime, List<DayOfWeek> workingDays) {
}
