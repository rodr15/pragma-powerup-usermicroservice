package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.spi.IRolePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleUseCaseTest {
    @Mock
    private IRolePersistencePort rolePersistencePort;
    private RoleUseCase roleUseCase;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        roleUseCase = new RoleUseCase(rolePersistencePort);
    }

    @Test
    void testGetAllRoles() {
        // Arrange
        Role role1 = new Role(1L,"Role1","Role1");
        Role role2 = new Role(2L,"Role2","Role2");
        List<Role> expectedRoles = Arrays.asList(role1, role2);

        when(rolePersistencePort.getAllRoles()).thenReturn(expectedRoles);

        RoleUseCase roleUseCase = new RoleUseCase(rolePersistencePort);

        // Act
        List<Role> actualRoles = roleUseCase.getAllRoles();

        // Assert
        assertEquals(expectedRoles, actualRoles);
        verify(rolePersistencePort, times(1)).getAllRoles();
    }
}