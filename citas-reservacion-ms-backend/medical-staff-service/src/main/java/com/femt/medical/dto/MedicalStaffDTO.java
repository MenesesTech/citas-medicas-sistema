package com.femt.medical.dto;

import java.time.LocalTime;
import java.util.List;

import com.femt.medical.models.MedicalStaff.DayOfWeek;
import com.femt.medical.models.Specialty;

public record MedicalStaffDTO(
                Long id,
                Long userId,
                String phone,
                Specialty specialty,
                LocalTime startTime,
                LocalTime endTime,
                List<DayOfWeek> workingDays,
                boolean active) {
}
