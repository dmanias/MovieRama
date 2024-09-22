package com.movierama.infrastructure.mapper;

import com.movierama.domain.model.Movie;
import com.movierama.domain.model.User;
import com.movierama.infrastructure.entity.MovieEntity;
import com.movierama.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class MovieEntityMapper {

    public MovieEntity toEntity(Movie movie) {
        if (movie == null) {
            return null;
        }

        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(movie.getId());
        movieEntity.setTitle(movie.getTitle());
        movieEntity.setDescription(movie.getDescription());
        movieEntity.setPublicationDate(movie.getPublicationDate());

        if (movie.getSubmittedBy() != null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(movie.getSubmittedBy().getId());
            userEntity.setUsername(movie.getSubmittedBy().getUsername());
            userEntity.setEmail(movie.getSubmittedBy().getEmail());
            movieEntity.setSubmittedBy(userEntity);
        }

        movieEntity.setLikeCount(movie.getLikeCount());
        movieEntity.setHateCount(movie.getHateCount());

        return movieEntity;
    }

    public Movie toDomain(MovieEntity movieEntity) {
        if (movieEntity == null) {
            return null;
        }

        User submittedBy = null;
        if (movieEntity.getSubmittedBy() != null) {
            submittedBy = new User(
                    movieEntity.getSubmittedBy().getId(),
                    movieEntity.getSubmittedBy().getUsername(),
                    null, // We don't need to set the password here
                    movieEntity.getSubmittedBy().getEmail()
            );
        }

        Movie movie = new Movie(
                movieEntity.getId(),
                movieEntity.getTitle(),
                movieEntity.getDescription(),
                submittedBy,
                movieEntity.getPublicationDate()
        );

        movie.setLikeCount(movieEntity.getLikeCount());
        movie.setHateCount(movieEntity.getHateCount());

        return movie;
    }
}