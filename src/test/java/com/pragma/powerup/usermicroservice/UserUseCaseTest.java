package com.pragma.powerup.usermicroservice;

import com.pragma.powerup.usermicroservice.domain.exceptions.NotLegalAgeException;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecase.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserUseCaseTest {
    @Mock
    private IUserPersistencePort userPersistencePort;

    private UserUseCase userUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userUseCase = new UserUseCase(userPersistencePort);
    }

    @Test
    public void testSaveUser_ValidUser_SaveSuccessful() {
        // Arrange
        User user = new User(
                1L,
                "Name",
                "Surname",
                "st 123 # 456",
                "123",
                LocalDate.of(2000,10,01),
                "1",
                "email@some.com",
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
                1L,
                "Name",
                "Surname",
                "st 123 # 456",
                "123",
                LocalDate.of(2000,10,01),
                "1",
                "email@some.com",
                new Role( 3L,"ROLE_OWNER","ROLE_OWNER" )

        );

        // Act & Assert
        assertThrows(NotLegalAgeException.class, () -> userUseCase.saveUser(user));
        Mockito.verifyNoInteractions(userPersistencePort);
    }
}
