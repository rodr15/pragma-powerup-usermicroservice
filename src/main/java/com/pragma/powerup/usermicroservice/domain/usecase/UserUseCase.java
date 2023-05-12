package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.NotLegalAgeException;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Period;

import static com.pragma.powerup.usermicroservice.configuration.Constants.LEGAL_AGE;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final PasswordEncoder passwordEncoder;
    public UserUseCase(IUserPersistencePort personPersistencePort, PasswordEncoder passwordEncoder) {
        this.userPersistencePort = personPersistencePort;
        this.passwordEncoder = passwordEncoder;
    }

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
    public Role getRoleByUserId(String userDni) {

        User user = userPersistencePort.getUser(userDni);
        return user.getRole();
    }
}
