package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.NotLegalAgeException;
import com.pragma.powerup.usermicroservice.domain.exceptions.NotRestaurantOwnerException;
import com.pragma.powerup.usermicroservice.domain.gateway.IPlazoletaClient;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Period;

import static com.pragma.powerup.usermicroservice.configuration.Constants.LEGAL_AGE;

@AllArgsConstructor
public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IPlazoletaClient plazoletaClient;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User saveUser(User user) {
        LocalDate currentDate = LocalDate.now();
        if(Period.between(user.getBirthDate(),currentDate).getYears() < LEGAL_AGE ){
            throw new NotLegalAgeException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userPersistencePort.saveUser(user);
    }

    @Override
    public User saveUserEmployee(User user,String ownerId,Long restaurantId) {

        boolean isOwner = plazoletaClient.verifyOwner( ownerId , restaurantId );

        if(!isOwner){
            throw new NotRestaurantOwnerException();
        }

        saveUser( user );
        plazoletaClient.assignRestaurantEmployee( user.getDniNumber() , restaurantId );
        return user;
    }

    @Override
    public Role getRoleByUserId(String userDni) {

        User user = userPersistencePort.getUser(userDni);
        return user.getRole();
    }
}
