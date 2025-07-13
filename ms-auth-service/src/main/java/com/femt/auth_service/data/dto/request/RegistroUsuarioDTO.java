package com.femt.auth_service.data.dto.request;

import com.femt.auth_service.data.model.Rol;

public record RegistroUsuarioDTO(String email, String password, Rol rol, Long entidadId) {

}
