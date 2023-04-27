package com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.mapper;

import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.entity.PersonEntity;
import com.ti.acelera.usermicroservice.domain.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPersonEntityMapper {
    PersonEntity toEntity(Person person);
}
