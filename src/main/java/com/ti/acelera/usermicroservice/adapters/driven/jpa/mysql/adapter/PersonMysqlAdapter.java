package com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.exception.MailAlreadyExistsException;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.exception.PersonAlreadyExistsException;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.mapper.IPersonEntityMapper;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.repository.IPersonRepository;
import com.ti.acelera.usermicroservice.domain.model.Person;
import com.ti.acelera.usermicroservice.domain.spi.IPersonPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class PersonMysqlAdapter implements IPersonPersistencePort {
    private final IPersonRepository personRepository;
    private final IPersonEntityMapper personEntityMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void savePerson(Person person) {
        if (personRepository.findByDniNumber(person.getDniNumber()).isPresent()) {
            throw new PersonAlreadyExistsException();
        }
        if (personRepository.existsByMail(person.getMail())){
            throw new MailAlreadyExistsException();
        }

        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepository.save(personEntityMapper.toEntity(person));
    }
}
