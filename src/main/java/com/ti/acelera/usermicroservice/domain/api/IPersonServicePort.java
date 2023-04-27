package com.ti.acelera.usermicroservice.domain.api;

import com.ti.acelera.usermicroservice.domain.model.Person;

public interface IPersonServicePort {
    void savePerson(Person person);
}
