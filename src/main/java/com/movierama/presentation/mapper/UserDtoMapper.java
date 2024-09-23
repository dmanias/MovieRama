package com.movierama.presentation.mapper;

import com.movierama.domain.model.User;
import com.movierama.presentation.dto.UserRegistrationRequest;
import com.movierama.presentation.dto.LoginRequest;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public User toDomain(UserRegistrationRequest dto) {
        return new User(
                null, // id will be generated
                dto.getUsername(),
                dto.getPassword(),
                dto.getEmail()
        );
    }

    public User toDomain(LoginRequest dto) {
        return new User(
                null, // id is not needed for login
                dto.getUsername(),
                dto.getPassword(),
                null // email is not needed for login
        );
    }

    // Might add a method to convert from Domain to DTO if needed for responses
    // public UserResponseDto toDto(User user) { ... }
}