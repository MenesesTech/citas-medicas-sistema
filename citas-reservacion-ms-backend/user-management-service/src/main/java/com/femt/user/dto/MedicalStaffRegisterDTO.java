package com.femt.user.dto;

import java.time.LocalTime;
import java.util.List;

public record MedicalStaffRegisterDTO(Long userId, String phone, String specialty, LocalTime startTime,
        LocalTime endTime, List<String> workingDays) {

}
