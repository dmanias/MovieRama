// src/test/java/com/movierama/presentation/controller/UserControllerTest.java

package com.movierama.presentation.controller;

import com.movierama.application.usecase.RegisterUserUseCase;
import com.movierama.domain.model.User;
import com.movierama.presentation.dto.UserRegistrationRequest;
import com.movierama.presentation.mapper.UserDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Mock
    private RegisterUserUseCase registerUserUseCase;
    @Mock
    private UserDtoMapper userDtoMapper;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;

    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(registerUserUseCase, userDtoMapper);
    }

    @Test
    void showRegistrationForm_ReturnsRegisterView() {
        String viewName = userController.showRegistrationForm(model);

        assertEquals("register", viewName);
        verify(model).addAttribute(eq("userRegistrationRequest"), any(UserRegistrationRequest.class));
    }

    @Test
    void registerUser_WithValidData_RedirectsToLogin() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("newuser");
        request.setPassword("password");
        request.setEmail("newuser@example.com");

        User user = new User(null, "newuser", "password", "newuser@example.com");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userDtoMapper.toDomain(request)).thenReturn(user);

        String viewName = userController.registerUser(request, bindingResult, model);

        assertEquals("redirect:/login?registered", viewName);
        verify(registerUserUseCase).execute(user);
    }

    @Test
    void registerUser_WithInvalidData_ReturnsRegisterView() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userController.registerUser(request, bindingResult, model);

        assertEquals("register", viewName);
        verify(registerUserUseCase, never()).execute(any());
    }
}