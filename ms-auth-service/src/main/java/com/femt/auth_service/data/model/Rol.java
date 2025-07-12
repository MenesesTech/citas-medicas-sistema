package com.femt.auth_service.data.model;

/**
 * ===================================================
 * Enumeración Rol
 * ===================================================
 * Define los diferentes tipos de roles de usuario
 * - ADMIN: Usuario administrativo
 * - DOCTOR: Usuario que pertenece al personal médico.
 * - PATIENT: Usuario que representa a un paciente.
 */
public enum Rol {
    ADMIN("admin"),
    DOCTOR("doctor"),
    PATIENT("patient");

    private String rol;

    // Constructor
    Rol(String rol) {
        this.rol = rol;
    }

    // Retorna el rol del usuario (String)
    public String getRol() {
        return this.rol;
    }
}
