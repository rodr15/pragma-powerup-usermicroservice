package com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.mapper;

import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.ti.acelera.usermicroservice.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoleEntityMapper {
    List<Role> toRoleList(List<RoleEntity> roleEntityList);
}
