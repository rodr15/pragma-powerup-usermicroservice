package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.MailAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.PersonAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.PersonNotFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IUserEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IUserRepository;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserMysqlAdapter implements IUserPersistencePort {
    private final IUserRepository personRepository;
    private final IUserEntityMapper personEntityMapper;


    @Override
    public User saveUser(User user) {
        if (personRepository.findByDniNumber(user.getDniNumber()).isPresent()) {
            throw new PersonAlreadyExistsException();
        }

        if (personRepository.existsByMail(user.getMail())) {
            throw new MailAlreadyExistsException();
        }

        personRepository.save(personEntityMapper.toEntity(user));
        return user;
    }

    @Override
    public User getUser(String dni) {

        Optional<UserEntity> user = personRepository.findByDniNumber(dni);

        if (!user.isPresent()) {
            throw new PersonNotFoundException();
        }

        return personEntityMapper.toUser(user.get());

    }
}
