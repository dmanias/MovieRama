package com.movierama.presentation.mapper;

import com.movierama.domain.model.Movie;
import com.movierama.domain.model.User;
import com.movierama.presentation.dto.MovieSubmissionRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MovieDtoMapper {

    public Movie toDomain(MovieSubmissionRequest dto, User user) {
        return new Movie(
                null, // id will be generated
                dto.getTitle(),
                dto.getDescription(),
                user,
                LocalDateTime.now()
        );
    }

    // Might add a method to convert from Domain to DTO if needed for responses
    // public MovieResponseDto toDto(Movie movie) { ... }
}