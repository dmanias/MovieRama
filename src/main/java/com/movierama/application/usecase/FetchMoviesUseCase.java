package com.movierama.application.usecase;

import com.movierama.domain.model.Movie;
import com.movierama.domain.service.MovieDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FetchMoviesUseCase {
    private static final Logger logger = LoggerFactory.getLogger(FetchMoviesUseCase.class);

    private final MovieDomainService movieDomainService;

    @Autowired
    public FetchMoviesUseCase(MovieDomainService movieDomainService) {
        this.movieDomainService = movieDomainService;
    }

    public List<Movie> getAllMovies() {
        logger.info("Fetching all movies");
        List<Movie> movies = movieDomainService.getAllMovies();
        logger.info("Fetched {} movies", movies.size());
        return movies;
    }

    public List<Movie> getMoviesByUser(Long userId) {
        logger.info("Fetching movies for user ID: {}", userId);
        List<Movie> movies = movieDomainService.getMoviesByUser(userId);
        logger.info("Fetched {} movies for user ID: {}", movies.size(), userId);
        return movies;
    }

    public List<Movie> getMoviesSorted(String sortBy) {
        logger.info("Fetching movies sorted by: {}", sortBy);
        List<Movie> movies = movieDomainService.getMoviesSorted(sortBy);
        logger.info("Fetched {} movies sorted by {}", movies.size(), sortBy);
        return movies;
    }
}