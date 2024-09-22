package com.movierama.application.usecase;

import com.movierama.domain.model.Movie;
import com.movierama.domain.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUserMoviesUseCase {
    private final MovieRepository movieRepository;

    public GetUserMoviesUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> execute(Long userId) {
        return movieRepository.findByUserId(userId);
    }
}