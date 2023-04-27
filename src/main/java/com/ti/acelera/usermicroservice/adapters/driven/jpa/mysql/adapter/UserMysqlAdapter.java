package com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.entity.PersonEntity;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.entity.UserEntity;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.exception.PersonNotFoundException;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.exception.RoleNotAllowedForCreationException;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.exception.RoleNotFoundException;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.exception.UserAlreadyExistsException;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.exception.UserNotFoundException;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.repository.IPersonRepository;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.ti.acelera.usermicroservice.domain.model.User;
import com.ti.acelera.usermicroservice.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ti.acelera.usermicroservice.configuration.Constants.CLIENT_ROLE_ID;
import static com.ti.acelera.usermicroservice.configuration.Constants.EMPLOYEE_ROLE_ID;
import static com.ti.acelera.usermicroservice.configuration.Constants.MAX_PAGE_SIZE;
import static com.ti.acelera.usermicroservice.configuration.Constants.PROVIDER_ROLE_ID;

@RequiredArgsConstructor
@Transactional
public class UserMysqlAdapter implements IUserPersistencePort {
    private final IUserRepository userRepository;
    private final IPersonRepository personRepository;
    private final IRoleRepository roleRepository;
    private final IUserEntityMapper userEntityMapper;
    @Override
    public void saveUser(User user) {
        if (user.getRole().getId().equals(PROVIDER_ROLE_ID))
        {
            throw new RoleNotAllowedForCreationException();
        }
        if (userRepository.findByPersonEntityIdAndRoleEntityId(user.getPerson().getId(), user.getRole().getId()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        personRepository.findById(user.getPerson().getId()).orElseThrow(PersonNotFoundException::new);
        roleRepository.findById(user.getRole().getId()).orElseThrow(RoleNotFoundException::new);
        userRepository.save(userEntityMapper.toEntity(user));
    }

    @Override
    public void deleteUser(User user) {
        if (userRepository.findByPersonEntityIdAndRoleEntityId(user.getPerson().getId(), user.getRole().getId()).isPresent()) {
            userRepository.deleteByPersonEntityIdAndRoleEntityId(user.getPerson().getId(), user.getRole().getId());
        }
        else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<User> getAllProviders(int page) {
        Pageable pagination = PageRequest.of(page, MAX_PAGE_SIZE);
        List<UserEntity> userEntityList = userRepository.findAllByRoleEntityId(PROVIDER_ROLE_ID, pagination);
        if (userEntityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return userEntityMapper.toUserList(userEntityList);
    }

    @Override
    public User getProvider(Long id) {
        UserEntity userEntity = userRepository.findByPersonEntityIdAndRoleEntityId(id, PROVIDER_ROLE_ID).orElseThrow(UserNotFoundException::new);
        return userEntityMapper.toUser(userEntity);
    }

    @Override
    public User getEmployee(Long id) {
        UserEntity userEntity = userRepository.findByPersonEntityIdAndRoleEntityId(id, EMPLOYEE_ROLE_ID).orElseThrow(UserNotFoundException::new);
        return userEntityMapper.toUser(userEntity);
    }

    @Override
    public User getClient(Long id) {
        UserEntity userEntity = userRepository.findByPersonEntityIdAndRoleEntityId(id, CLIENT_ROLE_ID).orElseThrow(UserNotFoundException::new);
        return userEntityMapper.toUser(userEntity);
    }
}
