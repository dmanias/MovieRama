package com.movierama.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void createUser_WithValidData_Succeeds() {
        User user = new User(1L, "john_doe", "password123", "john@example.com");
        assertNotNull(user, "User should not be null");
        assertEquals(1L, user.getId(), "ID should match the input");
        assertEquals("john_doe", user.getUsername(), "Username should match the input");
        assertEquals("password123", user.getPassword(), "Password should match the input");
        assertEquals("john@example.com", user.getEmail(), "Email should match the input");
    }

    @Test
    void createUser_WithOnlyId_Succeeds() {
        User user = new User(1L);
        assertNotNull(user, "User should not be null");
        assertEquals(1L, user.getId(), "ID should match the input");
        assertNull(user.getUsername(), "Username should be null");
        assertNull(user.getPassword(), "Password should be null");
        assertNull(user.getEmail(), "Email should be null");
    }

    @Test
    void setUsername_WithValidUsername_Succeeds() {
        User user = new User(1L);
        user.setUsername("john_doe");
        assertEquals("john_doe", user.getUsername(), "Username should be set correctly");
    }

    @Test
    void setUsername_WithNullUsername_Succeeds() {
        User user = new User(1L);
        user.setUsername(null);
        assertNull(user.getUsername(), "Username should be null");
    }

    @Test
    void setUsername_WithEmptyUsername_ThrowsException() {
        User user = new User(1L);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setUsername("");
        }, "Expected exception for empty username");
        assertEquals("Username cannot be empty if provided.", exception.getMessage());
    }

    @Test
    void setPassword_WithValidPassword_Succeeds() {
        User user = new User(1L);
        user.setPassword("password123");
        assertEquals("password123", user.getPassword(), "Password should be set correctly");
    }

    @Test
    void setPassword_WithNullPassword_Succeeds() {
        User user = new User(1L);
        user.setPassword(null);
        assertNull(user.getPassword(), "Password should be null");
    }

    @Test
    void setPassword_WithEmptyPassword_ThrowsException() {
        User user = new User(1L);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setPassword("");
        }, "Expected exception for empty password");
        assertEquals("Password cannot be empty if provided.", exception.getMessage());
    }

    @Test
    void setEmail_WithValidEmail_Succeeds() {
        User user = new User(1L);
        user.setEmail("john@example.com");
        assertEquals("john@example.com", user.getEmail(), "Email should be set correctly");
    }

    @Test
    void setEmail_WithNullEmail_Succeeds() {
        User user = new User(1L);
        user.setEmail(null);
        assertNull(user.getEmail(), "Email should be null");
    }

    @Test
    void setEmail_WithEmptyEmail_ThrowsException() {
        User user = new User(1L);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setEmail("");
        }, "Expected exception for empty email");
        assertEquals("Email cannot be empty if provided.", exception.getMessage());
    }

    @Test
    void userDetails_DefaultValues() {
        User user = new User(1L);
        assertTrue(user.isAccountNonExpired(), "Account should be non-expired by default");
        assertTrue(user.isAccountNonLocked(), "Account should be non-locked by default");
        assertTrue(user.isCredentialsNonExpired(), "Credentials should be non-expired by default");
        assertTrue(user.isEnabled(), "User should be enabled by default");
        assertNotNull(user.getAuthorities(), "Authorities should not be null");
        assertEquals(1, user.getAuthorities().size(), "User should have one authority");
        assertTrue(user.getAuthorities().iterator().next().getAuthority().equals("ROLE_USER"), "User should have ROLE_USER authority");
    }
}