package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.PersonResponse;

import java.util.List;

public interface IUserHandler {
    void saveUser(UserRequestDto userRequestDto);
    void deleteUser(UserRequestDto userRequestDto);
    List<PersonResponse> getProvider(Integer page);
    PersonResponse getProvider(Long id);
    PersonResponse getEmployee(Long id);
    PersonResponse getClient(Long id);

}
