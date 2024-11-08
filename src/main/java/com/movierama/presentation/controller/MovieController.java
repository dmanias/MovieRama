package com.movierama.presentation.controller;

import com.movierama.application.usecase.FetchMoviesUseCase;
import com.movierama.application.usecase.SubmitMovieUseCase;
import com.movierama.domain.model.Movie;
import com.movierama.domain.model.User;
import com.movierama.domain.repository.UserRepository;
import com.movierama.presentation.dto.MovieSubmissionRequest;
import com.movierama.presentation.mapper.MovieDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@Tag(name = "Movies", description = "Movie management APIs")
public class MovieController {
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    private final SubmitMovieUseCase submitMovieUseCase;
    private final FetchMoviesUseCase fetchMoviesUseCase;
    private final MovieDtoMapper movieDtoMapper;
    private final UserRepository userRepository;

    @Autowired
    public MovieController(SubmitMovieUseCase submitMovieUseCase, FetchMoviesUseCase fetchMoviesUseCase,
                           MovieDtoMapper movieDtoMapper, UserRepository userRepository) {
        this.submitMovieUseCase = submitMovieUseCase;
        this.fetchMoviesUseCase = fetchMoviesUseCase;
        this.movieDtoMapper = movieDtoMapper;
        this.userRepository = userRepository;
        logger.info("MovieController initialized");
    }

    @GetMapping("/")
    @Operation(summary = "Get home page", description = "Retrieves the home page with movies sorted by the specified criteria")
    public String home(Model model, @RequestParam(defaultValue = "date") String sortBy) {
        logger.info("Fetching movies for home page, sorted by: {}", sortBy);
        try {
            List<Movie> movies = fetchMoviesUseCase.getMoviesSorted(sortBy);
            logger.info("Fetched {} movies", movies.size());
            model.addAttribute("movies", movies);
            model.addAttribute("sortBy", sortBy);
            return "home";
        } catch (Exception e) {
            logger.error("Error fetching movies for home page: ", e);
            model.addAttribute("error", "An error occurred while fetching movies. Please try again later.");
            return "error";
        }
    }

    @GetMapping("/movies/add")
    @Operation(summary = "Show add movie form", description = "Displays the form to add a new movie")
    public String showAddMovieForm(Model model) {
        logger.info("Showing add movie form");
        model.addAttribute("movieSubmissionRequest", new MovieSubmissionRequest());
        return "add-movie";
    }

    @PostMapping("/movies/add")
    @Operation(summary = "Add a new movie", description = "Adds a new movie to the database")
    public String addMovie(@Valid @ModelAttribute("movieSubmissionRequest") MovieSubmissionRequest request,
                           Authentication authentication) {
        logger.info("Processing add movie request: {}", request.getTitle());
        try {
            User currentUser = (User) authentication.getPrincipal();
            Movie movie = movieDtoMapper.toDomain(request, currentUser);
            submitMovieUseCase.execute(movie);
            logger.info("Movie added successfully: {}", movie.getTitle());
            return "redirect:/";
        } catch (Exception e) {
            logger.error("Error adding movie: ", e);
            return "redirect:/movies/add?error";
        }
    }

    @GetMapping("/users/{userId}/movies")
    @Operation(summary = "Get user's movies", description = "Retrieves all movies submitted by a specific user")
    public String getMoviesByUser(@PathVariable Long userId, Model model) {
        logger.info("Fetching movies for user ID: {}", userId);
        try {
            List<Movie> userMovies = fetchMoviesUseCase.getMoviesByUser(userId);
            logger.info("Fetched {} movies for user ID: {}", userMovies.size(), userId);
            model.addAttribute("movies", userMovies);

            userRepository.findById(userId).ifPresent(user -> model.addAttribute("user", user));

            return "user-movies";
        } catch (Exception e) {
            logger.error("Error fetching user movies: ", e);
            model.addAttribute("error", "An error occurred while fetching user movies. Please try again later.");
            return "error";
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search movies", description = "Searches for movies based on the provided query")
    public String searchMovies(@RequestParam("query") String query, Model model) {
        logger.info("Searching movies with query: {}", query);
        try {
            List<Movie> searchResults = fetchMoviesUseCase.searchMovies(query);
            logger.info("Found {} movies matching the search query", searchResults.size());
            model.addAttribute("movies", searchResults);
            model.addAttribute("searchQuery", query);
            return "search-results";
        } catch (Exception e) {
            logger.error("Error searching movies: ", e);
            model.addAttribute("error", "An error occurred while searching for movies. Please try again later.");
            return "error";
        }
    }
}