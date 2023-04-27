package com.ti.acelera.usermicroservice.domain.api;

import com.ti.acelera.usermicroservice.domain.model.Role;

import java.util.List;

public interface IRoleServicePort {
    List<Role> getAllRoles();
}
