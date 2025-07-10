package com.femt.user.dto;

import com.femt.user.models.Rol;

public record UserProfileDTO(Long id, String name, String dni, String email, Rol rol) {

}
