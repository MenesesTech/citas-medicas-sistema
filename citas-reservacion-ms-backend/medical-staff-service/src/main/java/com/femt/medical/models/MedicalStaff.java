package com.femt.medical.models;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medical-staff")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class MedicalStaff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private Specialty specialty;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> workingDays;

    @Column(nullable = false)
    private boolean active = true;

    public MedicalStaff(String name, String dni, String email, String phone, Specialty specialty, LocalTime startTime,
            LocalTime endTime, List<DayOfWeek> workingDays, boolean active) {
        this.phone = phone;
        this.specialty = specialty;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workingDays = workingDays;
        this.active = active;
    }

    public enum DayOfWeek {
        LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO
    }
}
