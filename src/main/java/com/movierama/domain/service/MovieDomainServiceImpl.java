package com.movierama.domain.service;

import com.movierama.domain.model.Movie;
import com.movierama.domain.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieDomainServiceImpl implements MovieDomainService {
    private static final Logger logger = LoggerFactory.getLogger(MovieDomainServiceImpl.class);

    private final MovieRepository movieRepository;

    @Autowired
    public MovieDomainServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void submitMovie(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null.");
        }
        try {
            movieRepository.save(movie);
            logger.info("Movie saved successfully: {}", movie.getTitle());
        } catch (Exception e) {
            logger.error("Error saving movie: ", e);
            throw new RuntimeException("Failed to save movie: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Movie> getAllMovies() {
        try {
            List<Movie> movies = movieRepository.findAll();
            logger.info("Retrieved {} movies", movies.size());
            return movies;
        } catch (Exception e) {
            logger.error("Error retrieving all movies: ", e);
            throw new RuntimeException("Failed to retrieve movies: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Movie> getMoviesByUser(Long userId) {
        try {
            List<Movie> userMovies = movieRepository.findByUserId(userId);
            logger.info("Retrieved {} movies for user ID: {}", userMovies.size(), userId);
            return userMovies;
        } catch (Exception e) {
            logger.error("Error retrieving movies for user ID {}: ", userId, e);
            throw new RuntimeException("Failed to retrieve movies for user: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Movie> getMoviesSorted(String sortBy) {
        try {
            List<Movie> sortedMovies = movieRepository.findAllSorted(sortBy);
            logger.info("Retrieved {} movies sorted by {}", sortedMovies.size(), sortBy);
            return sortedMovies;
        } catch (Exception e) {
            logger.error("Error retrieving sorted movies: ", e);
            throw new RuntimeException("Failed to retrieve sorted movies: " + e.getMessage(), e);
        }
    }
}