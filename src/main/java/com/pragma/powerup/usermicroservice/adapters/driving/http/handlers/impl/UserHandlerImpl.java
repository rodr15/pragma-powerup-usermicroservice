package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.RoleResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IUserHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IRoleResponseMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IUserRequestMapper;
import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHandlerImpl implements IUserHandler {
    private final IUserServicePort personServicePort;
    private final IUserRequestMapper personRequestMapper;
    private final IRoleResponseMapper roleResponseMapper;

    @Override
    public void saveUserWithRole(UserRequestDto userRequestDto, Long idRole) {
        personServicePort.saveUser(personRequestMapper.toUserWithRole(userRequestDto, idRole));
    }

    @Override
    public RoleResponseDto getRoleByUserId(String userDni) {
       return  roleResponseMapper.toRoleResponseDto(personServicePort.getRoleByUserId(userDni));
    }


}
