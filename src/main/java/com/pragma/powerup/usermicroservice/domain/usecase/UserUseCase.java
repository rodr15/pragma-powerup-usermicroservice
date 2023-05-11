package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.NotLegalAgeException;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import static com.pragma.powerup.usermicroservice.configuration.Constants.LEGAL_AGE;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort personPersistencePort) {
        this.userPersistencePort = personPersistencePort;
    }

    @Override
    public User saveUser(User user) {
        LocalDate currentDate = LocalDate.now();
        if(Period.between(user.getBirthDate(),currentDate).getYears() < LEGAL_AGE ){
            throw new NotLegalAgeException();
        }

        return userPersistencePort.saveUser(user);
    }
}
