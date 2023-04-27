package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.LoginRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.JwtDto;

import java.text.ParseException;

public interface IAuthHandler {
    JwtDto login(LoginRequestDto loginRequestDto);
    JwtDto refresh(JwtDto jwtDto) throws ParseException;

}
