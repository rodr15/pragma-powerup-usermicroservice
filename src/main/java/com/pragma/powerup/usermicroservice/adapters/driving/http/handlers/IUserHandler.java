package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.RoleResponseDto;

public interface IUserHandler {
    void saveUserOwner(UserRequestDto personRequestDto);
    void saveUserEmployee(UserRequestDto userRequestDto,Long restaurantId);
    RoleResponseDto getRoleByUserId(String userDni);
}
