package com.pragma.powerup.usermicroservice.adapters.client.adapter;

import com.pragma.powerup.usermicroservice.adapters.client.exceptions.CouldNotAssignToRestaurant;
import com.pragma.powerup.usermicroservice.domain.gateway.IPlazoletaClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class PlazoletaClienteImpl implements IPlazoletaClient {
    @Value("${plazoleta.service.url}")
    private String plazoletaServiceUrl;

    @Override
    public boolean verifyOwner(String userId, Long restaurantId) {
        RestTemplate restTemplate = new RestTemplate();
        try {

            String url = String.format("%s/verify-owner?userId=%s&restaurantId=%s", plazoletaServiceUrl, userId, restaurantId);

            ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);

            return response.getBody();
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public void assignRestaurantEmployee(String employeeId, Long restaurantId) {
        RestTemplate restTemplate = new RestTemplate();
        try {

            String url = String.format("%s/add-employee?userId=%s&restaurantId=%s", plazoletaServiceUrl, employeeId, restaurantId);

            restTemplate.put(url, Boolean.class);
        } catch (Exception e) {
            throw new CouldNotAssignToRestaurant();
        }
    }
}



