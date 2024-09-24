package com.movierama.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Tag(name = "Test", description = "Test Thymeleaf API")
public class TestController {

    @GetMapping("/test")
    @Operation(summary = "Test Thymeleaf", description = "Displays a test page to verify Thymeleaf is working")
    public String test(Model model) {
        model.addAttribute("message", "If you can see this, Thymeleaf is working!");
        return "test";
    }
}