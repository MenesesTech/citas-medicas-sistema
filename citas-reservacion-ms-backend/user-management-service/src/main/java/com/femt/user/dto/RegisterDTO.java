package com.femt.user.dto;

import com.femt.user.models.Rol;

public record RegisterDTO(String name, String dni, String email, String password, Rol rol) {

}
