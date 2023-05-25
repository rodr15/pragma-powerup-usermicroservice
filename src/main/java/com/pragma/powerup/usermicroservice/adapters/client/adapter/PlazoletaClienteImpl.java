package com.pragma.powerup.usermicroservice.adapters.client.adapter;

import com.pragma.powerup.usermicroservice.adapters.client.exceptions.CouldNotAssignToRestaurant;
import com.pragma.powerup.usermicroservice.domain.gateway.IPlazoletaClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class PlazoletaClienteImpl implements IPlazoletaClient {
    @Value("${plazoleta.service.url}")
    private String plazoletaServiceUrl;

//    @Override
//    public String AssignRestaurantEmployee(String userDni, String restaurantId) {
//        RestTemplate restTemplate = new RestTemplate();
//        try {
//            String url = String.format("%s/user-role/%s", plazoletaServiceUrl, restaurantId, userDni);
//            String userRoleDto = restTemplate.getForObject(url, String.class);
////            restTemplate.postForEntity( url , {}, String.class  );
//            if (userRoleDto == null) {
//                throw new CouldNotAssignToRestaurant();
//            }
////            return userRoleDto.getName();
//        } catch (Exception e) {
//            throw new CouldNotAssignToRestaurant();
//        }
//        return "BIEN";
//    }

    @Override
    public boolean verifyOwner(String userId, Long restaurantId) {
        RestTemplate restTemplate = new RestTemplate();
        try {

            String url = String.format("%s/verify-owner?userId=%s&restaurantId=%s", plazoletaServiceUrl,userId,restaurantId);

            ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);

            return response.getBody();
        } catch (Exception e) {
            return false;
        }

    }
}



