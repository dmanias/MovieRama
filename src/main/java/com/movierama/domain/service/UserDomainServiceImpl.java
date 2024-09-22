// Domain Layer: src/main/java/com/movierama/domain/service/UserDomainServiceImpl.java

package com.movierama.domain.service;

import com.movierama.domain.model.User;
import com.movierama.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDomainServiceImpl implements UserDomainService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDomainServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username already exists.");
        }

        Optional<User> existingEmailUser = userRepository.findByEmail(user.getEmail());
        if (existingEmailUser.isPresent()) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}