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
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MovieControllerTest {

    @Mock
    private SubmitMovieUseCase submitMovieUseCase;
    @Mock
    private FetchMoviesUseCase fetchMoviesUseCase;
    @Mock
    private MovieDtoMapper movieDtoMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;

    private MovieController movieController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movieController = new MovieController(submitMovieUseCase, fetchMoviesUseCase, movieDtoMapper, userRepository);
    }

    @Test
    void home_ReturnsHomeViewWithMovies() {
        List<Movie> movies = Arrays.asList(
                new Movie(1L, "Movie 1", "Description 1", new User(1L), LocalDateTime.now()),
                new Movie(2L, "Movie 2", "Description 2", new User(2L), LocalDateTime.now())
        );
        when(fetchMoviesUseCase.getMoviesSorted(anyString())).thenReturn(movies);

        String viewName = movieController.home(model, "date");

        assertEquals("home", viewName);
        verify(model).addAttribute("movies", movies);
        verify(model).addAttribute("sortBy", "date");
    }

    @Test
    void addMovie_WithValidData_RedirectsToHome() {
        MovieSubmissionRequest request = new MovieSubmissionRequest();
        request.setTitle("New Movie");
        request.setDescription("New Description");

        User currentUser = new User(1L);
        Movie movie = new Movie(null, "New Movie", "New Description", currentUser, LocalDateTime.now());

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(currentUser);

        when(movieDtoMapper.toDomain(request, currentUser)).thenReturn(movie);

        String viewName = movieController.addMovie(request, authentication);

        assertEquals("redirect:/", viewName);
        verify(submitMovieUseCase).execute(movie);
    }

    @Test
    void addMovie_WithInvalidData_RedirectsToAddMovieWithError() {
        MovieSubmissionRequest request = new MovieSubmissionRequest();
        Authentication authentication = mock(Authentication.class);
        User currentUser = new User(1L);
        when(authentication.getPrincipal()).thenReturn(currentUser);

        when(movieDtoMapper.toDomain(request, currentUser)).thenThrow(new RuntimeException("Invalid data"));

        String viewName = movieController.addMovie(request, authentication);

        assertEquals("redirect:/movies/add?error", viewName);
        verify(submitMovieUseCase, never()).execute(any());
    }
}