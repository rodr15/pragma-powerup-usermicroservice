package com.ti.acelera.usermicroservice.domain.spi;

import com.ti.acelera.usermicroservice.domain.model.Role;

import java.util.List;

public interface IRolePersistencePort {
    List<Role> getAllRoles();
}
