package com.pragma.powerup.usermicroservice;

import com.pragma.powerup.usermicroservice.domain.exceptions.NotLegalAgeException;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;
    private PasswordEncoder passwordEncoder;
    private UserUseCase userUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userUseCase = new UserUseCase(userPersistencePort, passwordEncoder);
    }
    @Test
    public void testSaveUser_ValidUser_SaveSuccessful() {
        // Arrange
        User user = new User(
                3L,
                "Prueba",
                "Surname",
                "123123123",
                "3000000000",
                LocalDate.of(2000,10,01),
                "prueba@some.com",
                "123",
                new Role( 3L,"ROLE_OWNER","ROLE_OWNER" )

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
    public void testSaveUser_UnderLegalAge_ThrowsNotLegalAgeException() {
        // Arrange
        User user = new User(
                3L,
                "Prueba",
                "Surname",
                "123123123",
                "3000000000",
                LocalDate.of(2000,10,01),
                "prueba@some.com",
                "123",
                new Role( 3L,"ROLE_OWNER","ROLE_OWNER" )

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
                LocalDate.of(2000,10,01),
                "prueba@some.com",
                "123",
                new Role( 3L,"ROLE_OWNER","ROLE_OWNER" )

        );

        when(userPersistencePort.getUser(userDni)).thenReturn(user);

        // Act
        Role result = userUseCase.getRoleByUserId(userDni);

        // Assert
        assertNotNull(result);
        assertEquals(user.getRole(), result);
        verify(userPersistencePort).getUser(userDni);
    }

}
