package com.pragma.powerup.usermicroservice.domain.gateway;

public interface IPlazoletaClient {

    boolean verifyOwner(String userId, Long restaurantId);
    void assignRestaurantEmployee( String employeeId,Long restaurantId );
}
