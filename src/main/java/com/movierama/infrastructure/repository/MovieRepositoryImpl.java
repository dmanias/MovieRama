package com.movierama.infrastructure.repository;

import com.movierama.domain.model.Movie;
import com.movierama.domain.repository.MovieRepository;
import com.movierama.infrastructure.entity.MovieEntity;
import com.movierama.infrastructure.mapper.MovieEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MovieRepositoryImpl implements MovieRepository {
    private static final Logger logger = LoggerFactory.getLogger(MovieRepositoryImpl.class);

    private final MovieJpaRepository movieJpaRepository;
    private final MovieEntityMapper movieEntityMapper;

    @Autowired
    public MovieRepositoryImpl(MovieJpaRepository movieJpaRepository, MovieEntityMapper movieEntityMapper) {
        this.movieJpaRepository = movieJpaRepository;
        this.movieEntityMapper = movieEntityMapper;
    }

    @Override
    public Movie save(Movie movie) {
        try {
            MovieEntity movieEntity = movieEntityMapper.toEntity(movie);
            MovieEntity savedEntity = movieJpaRepository.save(movieEntity);
            logger.info("Movie entity saved successfully: {}", savedEntity.getId());
            return movieEntityMapper.toDomain(savedEntity);
        } catch (Exception e) {
            logger.error("Error saving movie entity: ", e);
            throw new RuntimeException("Failed to save movie entity: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Movie> findById(Long id) {
        try {
            Optional<MovieEntity> movieEntity = movieJpaRepository.findById(id);
            logger.info("Found movie entity with ID {}: {}", id, movieEntity.isPresent());
            return movieEntity.map(movieEntityMapper::toDomain);
        } catch (Exception e) {
            logger.error("Error finding movie by ID {}: ", id, e);
            throw new RuntimeException("Failed to find movie by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Movie> findAll() {
        try {
            List<MovieEntity> movieEntities = movieJpaRepository.findAll();
            logger.info("Found {} movie entities", movieEntities.size());
            return movieEntities.stream()
                    .map(movieEntityMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error finding all movies: ", e);
            throw new RuntimeException("Failed to find all movies: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Movie> findByUserId(Long userId) {
        try {
            List<MovieEntity> movieEntities = movieJpaRepository.findBySubmittedById(userId);
            logger.info("Found {} movie entities for user ID {}", movieEntities.size(), userId);
            return movieEntities.stream()
                    .map(movieEntityMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error finding movies by user ID {}: ", userId, e);
            throw new RuntimeException("Failed to find movies by user ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Movie> findAllSorted(String sortBy) {
        try {
            List<MovieEntity> sortedEntities;
            switch (sortBy.toLowerCase()) {
                case "likes":
                    sortedEntities = movieJpaRepository.findAllOrderByLikesDesc();
                    break;
                case "hates":
                    sortedEntities = movieJpaRepository.findAllOrderByHatesDesc();
                    break;
                case "date":
                default:
                    sortedEntities = movieJpaRepository.findAllOrderByPublicationDateDesc();
            }
            logger.info("Found {} sorted movie entities by {}", sortedEntities.size(), sortBy);
            return sortedEntities.stream()
                    .map(movieEntityMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error finding sorted movies: ", e);
            throw new RuntimeException("Failed to find sorted movies: " + e.getMessage(), e);
        }
    }
    @Override
    public List<Movie> searchMovies(String query) {
        List<MovieEntity> movieEntities = movieJpaRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
        return movieEntities.stream()
                .map(movieEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}