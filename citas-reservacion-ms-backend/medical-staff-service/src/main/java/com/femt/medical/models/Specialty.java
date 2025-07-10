package com.femt.medical.models;

public enum Specialty {
    CARDIOLOGIA("Cardiología"),
    DERMATOLOGIA("Dermatología"),
    GINECOLOGIA("Ginecología"),
    NEUROLOGIA("Neurología"),
    PEDIATRIA("Pediatría"),
    TRAUMATOLOGIA("Traumatología"),
    MEDICINA_GENERAL("Medicina General"),
    PSICOLOGIA("Psicología"),
    OFTALMOLOGIA("Oftalmología"),
    UROLOGIA("Urología");

    private String specialty;

    Specialty(String specialty) {
        this.specialty = specialty;
    }

    public String getSpecialty() {
        return this.specialty;
    }

}
