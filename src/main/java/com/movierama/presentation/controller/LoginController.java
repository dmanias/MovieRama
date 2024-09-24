package com.movierama.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@Tag(name = "Authentication", description = "Authentication endpoints")
public class LoginController {

    @GetMapping
    @Operation(summary = "Show login form", description = "Displays the login form")
    public String showLoginForm() {
        return "login";
    }

    // The actual login process will be handled by Spring Security
}