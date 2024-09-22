// Presentation Layer: src/main/java/com/movierama/presentation/dto/UserRegistrationRequest.java

package com.movierama.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for user registration request.
 */
@Getter
@Setter
public class UserRegistrationRequest {

    @NotBlank(message = "Username is required.")
    private String username;

    @NotBlank(message = "Password is required.")
    private String password;

    @Email(message = "Invalid email format.")
    @NotBlank(message = "Email is required.")
    private String email;
}
