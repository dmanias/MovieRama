package com.movierama.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("message", "If you can see this, Thymeleaf is working!");
        return "test";
    }
}