package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.RoleResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IUserHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IRoleResponseMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IUserRequestMapper;
import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import com.pragma.powerup.usermicroservice.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.pragma.powerup.usermicroservice.configuration.Constants.*;

@Service
@RequiredArgsConstructor
public class UserHandlerImpl implements IUserHandler {
    private final IUserServicePort personServicePort;
    private final IUserRequestMapper personRequestMapper;
    private final IRoleResponseMapper roleResponseMapper;

    @Override
    public void saveUserClient(UserRequestDto personRequestDto) {
        personServicePort.saveUser(personRequestMapper.toUserWithRole(personRequestDto, CLIENT_ROLE_ID));
    }

    @Override
    public void saveUserOwner(UserRequestDto userRequestDto) {
        personServicePort.saveUser(personRequestMapper.toUserWithRole(userRequestDto, OWNER_ROLE_ID));
    }

    @Override
    public void saveUserEmployee(UserRequestDto userRequestDto,String ownerId, Long restaurantId) {
        User employee = personRequestMapper.toUserWithRole(userRequestDto, EMPLOYEE_ROLE_ID);
        personServicePort.saveUserEmployee(employee,ownerId,restaurantId);
    }

    @Override
    public RoleResponseDto getRoleByUserId(String userDni) {
        return roleResponseMapper.toRoleResponseDto(personServicePort.getRoleByUserId(userDni));
    }

    @Override
    public String getUserPhone(String userDni) {
        return personServicePort.getUserPhone(userDni);
    }


}
