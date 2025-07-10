package com.femt.user.models;

public enum Rol {
    ADMIN("admin"),
    USER("user"),
    DOCTOR("doctor");

    private String rol;

    Rol(String rol) {
        this.rol = rol;
    }

    public String getRol() {
        return this.rol;
    }
}
