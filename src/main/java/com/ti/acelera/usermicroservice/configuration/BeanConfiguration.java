package com.ti.acelera.usermicroservice.configuration;

import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.adapter.PersonMysqlAdapter;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.adapter.RoleMysqlAdapter;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.adapter.UserMysqlAdapter;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.mapper.IPersonEntityMapper;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.mapper.IRoleEntityMapper;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.repository.IPersonRepository;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.ti.acelera.usermicroservice.domain.api.IPersonServicePort;
import com.ti.acelera.usermicroservice.domain.api.IRoleServicePort;
import com.ti.acelera.usermicroservice.domain.api.IUserServicePort;
import com.ti.acelera.usermicroservice.domain.spi.IPersonPersistencePort;
import com.ti.acelera.usermicroservice.domain.spi.IRolePersistencePort;
import com.ti.acelera.usermicroservice.domain.spi.IUserPersistencePort;
import com.ti.acelera.usermicroservice.domain.usecase.PersonUseCase;
import com.ti.acelera.usermicroservice.domain.usecase.RoleUseCase;
import com.ti.acelera.usermicroservice.domain.usecase.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRoleRepository roleRepository;
    private final IPersonRepository personRepository;
    private final IUserRepository userRepository;
    private final IRoleEntityMapper roleEntityMapper;
    private final IPersonEntityMapper personEntityMapper;
    private final IUserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;
    @Bean
    public IRoleServicePort roleServicePort() {
        return new RoleUseCase(rolePersistencePort());
    }
    @Bean
    public IRolePersistencePort rolePersistencePort() {
        return new RoleMysqlAdapter(roleRepository, roleEntityMapper);
    }
    @Bean
    public IPersonServicePort personServicePort() {
        return new PersonUseCase(personPersistencePort());
    }
    @Bean
    public IPersonPersistencePort personPersistencePort() {
        return new PersonMysqlAdapter(personRepository, personEntityMapper, passwordEncoder);
    }
    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort());
    }
    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserMysqlAdapter(userRepository, personRepository, roleRepository, userEntityMapper);
    }
}
