package com.ti.acelera.usermicroservice.domain.spi;

import com.ti.acelera.usermicroservice.domain.model.Person;

public interface IPersonPersistencePort {
    void savePerson(Person person);
}
