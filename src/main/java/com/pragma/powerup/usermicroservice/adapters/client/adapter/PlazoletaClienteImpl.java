package com.pragma.powerup.usermicroservice.adapters.client.adapter;

import com.ti.acelera.plazoletamicroservice.adapters.driver.client.dto.UserRoleDto;
import com.ti.acelera.plazoletamicroservice.adapters.driver.client.exceptions.UserNotFoundException;
import com.ti.acelera.plazoletamicroservice.domain.gateway.IUserClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;


public class PlazoletaClienteImpl implements IPlazoletaClient {
    @Value("${plazoleta.service.url}")
    private String plazoletaServiceUrl;

    public String getRoleByDni(String userDni) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String url = String.format("%s/user-role/%s", userServiceUrl, userDni);
            UserRoleDto userRoleDto = restTemplate.getForObject(url, UserRoleDto.class);
            if (userRoleDto == null) {
                throw new UserNotFoundException();
            }
            return userRoleDto.getName();
        } catch (Exception e) {
            throw new UserNotFoundException();
        }

    }


}



