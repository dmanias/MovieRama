package com.movierama.domain.service;

import com.movierama.domain.model.User;
import com.movierama.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserDomainServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserDomainService userDomainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDomainService = new UserDomainServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void registerUser_WithUniqueUsernameAndEmail_Succeeds() {
        // Arrange
        User user = new User(null, "john_doe", "password123", "john@example.com");
        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        // Act
        userDomainService.registerUser(user);

        // Assert
        verify(userRepository, times(1)).save(user);
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void registerUser_WithExistingUsername_ThrowsException() {
        // Arrange
        User user = new User(null, "john_doe", "password123", "john@example.com");
        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(new User(1L, "john_doe", "existingPassword", "existing@example.com")));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userDomainService.registerUser(user);
        });

        assertEquals("Username already exists.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_WithExistingEmail_ThrowsException() {
        // Arrange
        User user = new User(null, "john_doe", "password123", "john@example.com");
        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(new User(1L, "existing_user", "existingPassword", "john@example.com")));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userDomainService.registerUser(user);
        });

        assertEquals("Email is already in use.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_WithNullUser_ThrowsException() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userDomainService.registerUser(null);
        });

        assertEquals("User cannot be null.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    // Add more tests for other methods like updateUser, deleteUser, etc.
}