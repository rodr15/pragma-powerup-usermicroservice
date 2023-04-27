package com.ti.acelera.usermicroservice.adapters.driving.http.mapper;

import com.ti.acelera.usermicroservice.adapters.driving.http.dto.request.UserRequest;
import com.ti.acelera.usermicroservice.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {
    @Mapping(target = "person.id", source = "idPerson")
    @Mapping(target = "role.id", source = "idRole")
    User toUser(UserRequest userRequest);
}
