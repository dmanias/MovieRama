package com.movierama.application.usecase;

import com.movierama.domain.model.User;
import com.movierama.domain.service.UserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {

    private final UserDomainService userDomainService;

    @Autowired
    public RegisterUserUseCase(UserDomainService userDomainService) {
        this.userDomainService = userDomainService;
    }

    public void execute(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        try {
            userDomainService.registerUser(user);
        } catch (IllegalArgumentException e) {
            // Rethrow the exception to be handled by the controller
            throw e;
        } catch (Exception e) {
            // Log the exception and throw a more generic error
            // logger.error("Error registering user", e);
            throw new RuntimeException("Failed to register user. Please try again.");
        }
    }
}