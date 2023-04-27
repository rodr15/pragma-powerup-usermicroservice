package com.ti.acelera.usermicroservice.adapters.driving.http.mapper;

import com.ti.acelera.usermicroservice.adapters.driving.http.dto.response.RoleResponse;
import com.ti.acelera.usermicroservice.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoleResponseMapper {
    List<RoleResponse> toResponseList(List<Role> roleList);
}
