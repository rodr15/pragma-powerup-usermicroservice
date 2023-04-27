package com.ti.acelera.usermicroservice.adapters.driving.http.mapper;

import com.ti.acelera.usermicroservice.adapters.driving.http.dto.request.PersonRequest;
import com.ti.acelera.usermicroservice.domain.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPersonRequestMapper {
    Person toPerson(PersonRequest personRequest);
}
