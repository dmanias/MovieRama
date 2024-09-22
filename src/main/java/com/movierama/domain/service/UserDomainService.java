// Domain Layer: src/main/java/com/movierama/domain/service/UserDomainService.java

package com.movierama.domain.service;

import com.movierama.domain.model.User;

/**
 * Interface for user-related business operations.
 */
public interface UserDomainService {
    /**
     * Registers a new user in the system.
     *
     * @param user the user to register (must not be null)
     */
    void registerUser(User user);

    // Other user-related business methods can be added here
}
