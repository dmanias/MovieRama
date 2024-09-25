package com.movierama.domain.repository;

import com.movierama.domain.model.Movie;
import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    Movie save(Movie movie);
    Optional<Movie> findById(Long id);
    List<Movie> findAll();
    List<Movie> findByUserId(Long userId);
    List<Movie> findAllSorted(String sortBy);
    List<Movie> searchMovies(String query);
}