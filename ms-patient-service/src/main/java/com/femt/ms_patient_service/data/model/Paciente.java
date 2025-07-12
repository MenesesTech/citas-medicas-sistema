package com.femt.ms_patient_service.data.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "paciente")
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID usuarioUUID;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String distrito;

    public Paciente(UUID usuarioUUID, String nombre, String apellidos, String dni, String ciudad, String distrito) {
        this.usuarioUUID = usuarioUUID;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.ciudad = ciudad;
        this.distrito = distrito;
    }

}
