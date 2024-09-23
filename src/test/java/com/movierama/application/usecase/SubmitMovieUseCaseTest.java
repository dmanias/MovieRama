// SubmitMovieUseCaseTest.java
package com.movierama.application.usecase;

import com.movierama.domain.model.Movie;
import com.movierama.domain.model.User;
import com.movierama.domain.service.MovieDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SubmitMovieUseCaseTest {

    @Mock
    private MovieDomainService movieDomainService;

    private SubmitMovieUseCase submitMovieUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        submitMovieUseCase = new SubmitMovieUseCase(movieDomainService);
    }

    @Test
    void execute_WithValidMovie_CallsDomainService() {
        // Arrange
        User user = new User(1L, "testuser", "password", "test@example.com");
        LocalDateTime now = LocalDateTime.now();
        Movie movie = new Movie(null, "Test Movie", "Description", user, now);

        // Act
        submitMovieUseCase.execute(movie);

        // Assert
        verify(movieDomainService, times(1)).submitMovie(movie);
    }

    @Test
    void execute_WithNullMovie_ThrowsException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> submitMovieUseCase.execute(null));
        verify(movieDomainService, never()).submitMovie(any());
    }

    @Test
    void execute_WithEmptyTitle_ThrowsException() {
        // Arrange
        User user = new User(1L, "testuser", "password", "test@example.com");
        LocalDateTime now = LocalDateTime.now();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Movie movie = new Movie(null, "", "Description", user, now);
            submitMovieUseCase.execute(movie);
        });
        verify(movieDomainService, never()).submitMovie(any());
    }

    @Test
    void execute_WithNullTitle_ThrowsException() {
        // Arrange
        User user = new User(1L, "testuser", "password", "test@example.com");
        LocalDateTime now = LocalDateTime.now();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Movie movie = new Movie(null, null, "Description", user, now);
            submitMovieUseCase.execute(movie);
        });
        verify(movieDomainService, never()).submitMovie(any());
    }

    @Test
    void execute_WithEmptyDescription_ThrowsException() {
        // Arrange
        User user = new User(1L, "testuser", "password", "test@example.com");
        LocalDateTime now = LocalDateTime.now();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Movie movie = new Movie(null, "Test Movie", "", user, now);
            submitMovieUseCase.execute(movie);
        });
        verify(movieDomainService, never()).submitMovie(any());
    }

    @Test
    void execute_WithNullDescription_ThrowsException() {
        // Arrange
        User user = new User(1L, "testuser", "password", "test@example.com");
        LocalDateTime now = LocalDateTime.now();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Movie movie = new Movie(null, "Test Movie", null, user, now);
            submitMovieUseCase.execute(movie);
        });
        verify(movieDomainService, never()).submitMovie(any());
    }
}