package com.movierama.presentation.controller;

import com.movierama.application.usecase.RegisterUserUseCase;
import com.movierama.domain.model.User;
import com.movierama.presentation.dto.UserRegistrationRequest;
import com.movierama.presentation.mapper.UserDtoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@Tag(name = "Users", description = "User management APIs")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final RegisterUserUseCase registerUserUseCase;
    private final UserDtoMapper userDtoMapper;

    @Autowired
    public UserController(RegisterUserUseCase registerUserUseCase, UserDtoMapper userDtoMapper) {
        this.registerUserUseCase = registerUserUseCase;
        this.userDtoMapper = userDtoMapper;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        logger.info("Showing registration form");
        model.addAttribute("userRegistrationRequest", new UserRegistrationRequest());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userRegistrationRequest") UserRegistrationRequest request,
                               BindingResult bindingResult,
                               Model model) {
        logger.info("Processing registration request for username: {}", request.getUsername());

        if (bindingResult.hasErrors()) {
            logger.warn("Validation errors in registration form");
            return "register";
        }

        try {
            User user = userDtoMapper.toDomain(request);
            registerUserUseCase.execute(user);
            logger.info("User registered successfully: {}", user.getUsername());
            return "redirect:/login?registered";
        } catch (IllegalArgumentException e) {
            logger.error("Error during user registration: {}", e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
            return "register";
        } catch (Exception e) {
            logger.error("Unexpected error during user registration", e);
            model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
            return "register";
        }
    }
}