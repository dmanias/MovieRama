// MovieControllerTest.java
package com.movierama.presentation.controller;

import com.movierama.application.usecase.FetchMoviesUseCase;
import com.movierama.application.usecase.SubmitMovieUseCase;
import com.movierama.domain.model.Movie;
import com.movierama.domain.model.User;
import com.movierama.domain.repository.UserRepository;
import com.movierama.presentation.dto.MovieSubmissionRequest;
import com.movierama.presentation.mapper.MovieDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MovieControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SubmitMovieUseCase submitMovieUseCase;

    @Mock
    private FetchMoviesUseCase fetchMoviesUseCase;

    @Mock
    private MovieDtoMapper movieDtoMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    private MovieController movieController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movieController = new MovieController(submitMovieUseCase, fetchMoviesUseCase, movieDtoMapper, userRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    void getMovies_ReturnsListOfMovies() throws Exception {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        List<Movie> movies = Arrays.asList(
                new Movie(1L, "Movie 1", "Description 1", new User(1L), now),
                new Movie(2L, "Movie 2", "Description 2", new User(2L), now)
        );
        when(fetchMoviesUseCase.getMoviesSorted(anyString())).thenReturn(movies);

        // Act & Assert
        mockMvc.perform(get("/")
                        .param("sortBy", "date"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attribute("sortBy", "date"));

        verify(fetchMoviesUseCase).getMoviesSorted("date");
    }

    @Test
    void submitMovie_WithValidData_RedirectsToHome() throws Exception {
        // Arrange
        MovieSubmissionRequest request = new MovieSubmissionRequest();
        request.setTitle("Test Movie");
        request.setDescription("Test Description");

        User currentUser = new User(1L, "testuser", "password", "test@example.com");
        Movie movie = new Movie(null, "Test Movie", "Test Description", currentUser, LocalDateTime.now());

        when(authentication.getPrincipal()).thenReturn(currentUser);
        when(movieDtoMapper.toDomain(any(MovieSubmissionRequest.class), any(User.class))).thenReturn(movie);

        // Act & Assert
        mockMvc.perform(post("/movies/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "Test Movie")
                        .param("description", "Test Description")
                        .principal(authentication))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(submitMovieUseCase).execute(movie);
    }

    @Test
    void getMoviesByUser_ReturnsUserMovies() throws Exception {
        // Arrange
        Long userId = 1L;
        User user = new User(userId, "testuser", "password", "test@example.com");
        LocalDateTime now = LocalDateTime.now();
        List<Movie> userMovies = Arrays.asList(
                new Movie(1L, "User Movie 1", "Description 1", user, now),
                new Movie(2L, "User Movie 2", "Description 2", user, now)
        );

        when(fetchMoviesUseCase.getMoviesByUser(userId)).thenReturn(userMovies);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act & Assert
        mockMvc.perform(get("/users/{userId}/movies", userId))
                .andExpect(status().isOk())
                .andExpect(view().name("user-movies"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attributeExists("user"));

        verify(fetchMoviesUseCase).getMoviesByUser(userId);
        verify(userRepository).findById(userId);
    }
}