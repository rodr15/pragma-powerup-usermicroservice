package com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.repository;

import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {}
