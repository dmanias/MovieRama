package com.movierama.domain.service;

import com.movierama.domain.model.Movie;
import java.util.List;

public interface MovieDomainService {
    /**
     * Submits a new movie.
     *
     * @param movie the movie to submit (must not be null)
     */
    void submitMovie(Movie movie);

    /**
     * Retrieves all movies.
     *
     * @return a list of all movies
     */
    List<Movie> getAllMovies();

    /**
     * Retrieves all movies submitted by a specific user.
     *
     * @param userId the ID of the user
     * @return a list of movies submitted by the user
     */
    List<Movie> getMoviesByUser(Long userId);

    /**
     * Retrieves all movies sorted by the specified criteria.
     *
     * @param sortBy the criteria to sort by ("likes", "hates", or "date")
     * @return a sorted list of movies
     */
    List<Movie> getMoviesSorted(String sortBy);
    List<Movie> searchMovies(String query);
}