package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.model.User;

public interface IUserServicePort {
    User saveUser(User user);
    User saveUserEmployee(User user,String ownerId,Long restaurantId);

    Role getRoleByUserId(String userDni);

}
