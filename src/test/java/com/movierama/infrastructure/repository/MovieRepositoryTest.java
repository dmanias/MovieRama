// src/test/java/com/movierama/infrastructure/repository/MovieRepositoryTest.java

package com.movierama.infrastructure.repository;

import com.movierama.domain.model.Movie;
import com.movierama.domain.model.User;
import com.movierama.infrastructure.entity.MovieEntity;
import com.movierama.infrastructure.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MovieRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MovieJpaRepository movieRepository;

    @Test
    void findBySubmittedById_ReturnsCorrectMovies() {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("test@example.com");
        entityManager.persist(user);

        MovieEntity movie1 = new MovieEntity();
        movie1.setTitle("Movie 1");
        movie1.setDescription("Description 1");
        movie1.setSubmittedBy(user);
        movie1.setPublicationDate(LocalDateTime.now());
        entityManager.persist(movie1);

        MovieEntity movie2 = new MovieEntity();
        movie2.setTitle("Movie 2");
        movie2.setDescription("Description 2");
        movie2.setSubmittedBy(user);
        movie2.setPublicationDate(LocalDateTime.now());
        entityManager.persist(movie2);

        List<MovieEntity> foundMovies = movieRepository.findBySubmittedById(user.getId());

        assertEquals(2, foundMovies.size());
        assertTrue(foundMovies.stream().allMatch(movie -> movie.getSubmittedBy().equals(user)));
    }

    // Add more tests for other repository methods
}