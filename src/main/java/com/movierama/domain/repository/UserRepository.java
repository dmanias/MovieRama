package com.movierama.domain.repository;

import com.movierama.domain.model.User;
import java.util.Optional;

/**
 * Repository interface for User entity.
 */
public interface UserRepository {
    /**
     * Saves the given user.
     *
     * @param user the user to save (must not be null)
     * @return the saved user
     */
    User save(User user);

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for (must not be null)
     * @return an Optional containing the found user, or empty if not found
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by their email.
     *
     * @param email the email to search for (must not be null)
     * @return an Optional containing the found user, or empty if not found
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their ID.
     *
     * @param id the ID to search for (must not be null)
     * @return an Optional containing the found user, or empty if not found
     */
    Optional<User> findById(Long id);

    /**
     * Deletes a user.
     *
     * @param user the user to delete (must not be null)
     */
    void delete(User user);

    // Other data access methods can be added here as needed...
}