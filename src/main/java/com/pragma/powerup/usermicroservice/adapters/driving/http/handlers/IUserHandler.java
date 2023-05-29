package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.RoleResponseDto;

public interface IUserHandler {
    void saveUserClient(UserRequestDto personRequestDto);
    void saveUserOwner(UserRequestDto personRequestDto);
    void saveUserEmployee(UserRequestDto userRequestDto,String ownerId, Long restaurantId) ;
    RoleResponseDto getRoleByUserId(String userDni);
}
