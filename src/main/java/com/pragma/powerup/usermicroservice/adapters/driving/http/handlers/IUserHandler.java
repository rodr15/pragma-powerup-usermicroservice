package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.RoleResponseDto;

public interface IUserHandler {
    void saveUserWithRole(UserRequestDto personRequestDto, Long idRole);
    RoleResponseDto getRoleByUserId(String userDni);
}
