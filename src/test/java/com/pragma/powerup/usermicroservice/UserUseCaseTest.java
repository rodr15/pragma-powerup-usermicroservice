package com.pragma.powerup.usermicroservice;

import com.pragma.powerup.usermicroservice.domain.exceptions.NotLegalAgeException;
import com.pragma.powerup.usermicroservice.domain.exceptions.NotRestaurantOwnerException;
import com.pragma.powerup.usermicroservice.domain.gateway.IPlazoletaClient;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecase.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private IPlazoletaClient plazoletaClient;
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userUseCase = new UserUseCase(userPersistencePort, plazoletaClient, passwordEncoder);
    }

    @Test
    void testSaveUser_ValidUser_SaveSuccessful() {
        // Arrange
        User user = new User(
                3L,
                "Prueba",
                "Surname",
                "123123123",
                "3000000000",
                LocalDate.of(2000, 10, 01),
                "prueba@some.com",
                "123",
                new Role(3L, "ROLE_OWNER", "ROLE_OWNER")

        );
        when(userPersistencePort.saveUser(user)).thenReturn(user);

        // Act
        User savedUser = userUseCase.saveUser(user);

        // Assert
        verify(userPersistencePort).saveUser(user);
        assert savedUser != null;
        assert savedUser.getName().equals(user.getName());
        assert savedUser.getBirthDate().equals(user.getBirthDate());
    }

    @Test
    void testSaveUser_UnderLegalAge_ThrowsNotLegalAgeException() {
        // Arrange
        User user = new User(
                3L,
                "Prueba",
                "Surname",
                "123123123",
                "3000000000",
                LocalDate.of(2020, 10, 01),
                "prueba@some.com",
                "123",
                new Role(3L, "ROLE_OWNER", "ROLE_OWNER")

        );

        // Act & Assert
        assertThrows(NotLegalAgeException.class, () -> userUseCase.saveUser(user));
        Mockito.verifyNoInteractions(userPersistencePort);
    }

    @Test
    void getRoleByUserId_ValidUserId_ReturnsRole() {
        // Arrange
        String userDni = "123123123";
        User user = new User(
                3L,
                "Prueba",
                "Surname",
                "123123123",
                "3000000000",
                LocalDate.of(2000, 10, 01),
                "prueba@some.com",
                "123",
                new Role(3L, "ROLE_OWNER", "ROLE_OWNER")

        );

        when(userPersistencePort.getUser(userDni)).thenReturn(user);

        // Act
        Role result = userUseCase.getRoleByUserId(userDni);

        // Assert
        assertNotNull(result);
        assertEquals(user.getRole(), result);
        verify(userPersistencePort).getUser(userDni);
    }

    @Test
    void testSaveUserEmployee_ValidUserAndOwner_ReturnsUser() {
        // Arrange
        User user = new User(
                3L,
                "Prueba",
                "Surname",
                "123123123",
                "3000000000",
                LocalDate.of(2000, 10, 01),
                "prueba@some.com",
                "123",
                new Role(3L, "ROLE_OWNER", "ROLE_OWNER")

        );
        String ownerId = "123456789";
        Long restaurantId = 1L;

        when(plazoletaClient.verifyOwner(ownerId, restaurantId)).thenReturn(true);
        when(userPersistencePort.saveUser(user)).thenReturn(user);
        doNothing().when(plazoletaClient).assignRestaurantEmployee(user.getDniNumber(), restaurantId);

        // Act
        User result = userUseCase.saveUserEmployee(user, ownerId, restaurantId);

        // Assert
        assertNotNull(result);
        assertEquals(user, result);

        verify(plazoletaClient, times(1)).verifyOwner(ownerId, restaurantId);
        verify(userPersistencePort, times(1)).saveUser(user);
        verify(plazoletaClient, times(1)).assignRestaurantEmployee(user.getDniNumber(), restaurantId);
    }

    @Test
    void testSaveUserEmployee_ValidUserAndNonOwner_ThrowsException() {
        // Arrange
        User user = new User(
                3L,
                "Prueba",
                "Surname",
                "123123123",
                "3000000000",
                LocalDate.of(2000, 10, 01),
                "prueba@some.com",
                "123",
                new Role(1L, "ROLE_ADMIN", "ROLE_ADMIN")

        );

        String ownerId = "123456789";
        Long restaurantId = 1L;

        when(plazoletaClient.verifyOwner(ownerId, restaurantId)).thenReturn(false);

        // Act & Assert
        assertThrows(NotRestaurantOwnerException.class,
                () -> userUseCase.saveUserEmployee(user, ownerId, restaurantId));

        verify(plazoletaClient, times(1)).verifyOwner(ownerId, restaurantId);
        verifyNoInteractions(userPersistencePort);
    }

}
