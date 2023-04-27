package com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.mapper.IRoleEntityMapper;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.ti.acelera.usermicroservice.domain.model.Role;
import com.ti.acelera.usermicroservice.domain.spi.IRolePersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RoleMysqlAdapter implements IRolePersistencePort {
    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;
    @Override
    public List<Role> getAllRoles() {
        List<RoleEntity> roleEntityList = roleRepository.findAll();
        if (roleEntityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return roleEntityMapper.toRoleList(roleEntityList);
    }
}
