package com.ti.acelera.usermicroservice.domain.usecase;

import com.ti.acelera.usermicroservice.domain.api.IPersonServicePort;
import com.ti.acelera.usermicroservice.domain.model.Person;
import com.ti.acelera.usermicroservice.domain.spi.IPersonPersistencePort;

public class PersonUseCase implements IPersonServicePort {
    private final IPersonPersistencePort personPersistencePort;

    public PersonUseCase(IPersonPersistencePort personPersistencePort) {
        this.personPersistencePort = personPersistencePort;
    }

    @Override
    public void savePerson(Person person) {
        personPersistencePort.savePerson(person);
    }
}
