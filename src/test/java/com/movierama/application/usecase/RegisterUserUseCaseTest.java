package com.movierama.application.usecase;

import com.movierama.domain.model.User;
import com.movierama.domain.service.UserDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RegisterUserUseCaseTest {

    @Mock
    private UserDomainService userDomainService;

    private RegisterUserUseCase registerUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registerUserUseCase = new RegisterUserUseCase(userDomainService);
    }

    @Test
    void execute_WithValidUser_CallsDomainService() {
        // Arrange
        User user = new User(null, "testuser", "password", "test@example.com");

        // Act
        registerUserUseCase.execute(user);

        // Assert
        verify(userDomainService, times(1)).registerUser(user);
    }

    @Test
    void execute_WithNullUser_ThrowsException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> registerUserUseCase.execute(null));
        verify(userDomainService, never()).registerUser(any());
    }
}