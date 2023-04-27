package com.ti.acelera.usermicroservice.domain.usecase;

import com.ti.acelera.usermicroservice.domain.api.IRoleServicePort;
import com.ti.acelera.usermicroservice.domain.model.Role;
import com.ti.acelera.usermicroservice.domain.spi.IRolePersistencePort;

import java.util.List;

public class RoleUseCase implements IRoleServicePort {

    private final IRolePersistencePort rolePersistencePort;

    public RoleUseCase(IRolePersistencePort rolePersistencePort) {
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public List<Role> getAllRoles() {
        return rolePersistencePort.getAllRoles();
    }
}
