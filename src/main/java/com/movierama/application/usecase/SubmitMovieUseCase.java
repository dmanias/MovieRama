package com.movierama.application.usecase;

import com.movierama.domain.model.Movie;
import com.movierama.domain.service.MovieDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmitMovieUseCase {
    private static final Logger logger = LoggerFactory.getLogger(SubmitMovieUseCase.class);

    private final MovieDomainService movieDomainService;

    @Autowired
    public SubmitMovieUseCase(MovieDomainService movieDomainService) {
        this.movieDomainService = movieDomainService;
    }

    public void execute(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null.");
        }
        try {
            movieDomainService.submitMovie(movie);
        } catch (Exception e) {
            logger.error("Error submitting movie: ", e);
            throw new RuntimeException("Failed to submit movie: " + e.getMessage(), e);
        }
    }
}