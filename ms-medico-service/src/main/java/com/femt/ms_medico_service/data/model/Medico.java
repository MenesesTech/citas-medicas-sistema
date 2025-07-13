package com.femt.ms_medico_service.data.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "medicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreCompleto;

    @Column(nullable = false)
    private String especialidad;

    @Column(nullable = false)
    private String centroMedico;

    public Medico(String nombreCompleto, String especialidad, String centroMedico) {
        this.nombreCompleto = nombreCompleto;
        this.especialidad = especialidad;
        this.centroMedico = centroMedico;
    }

}
