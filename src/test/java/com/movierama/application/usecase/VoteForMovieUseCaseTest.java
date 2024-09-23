// VoteForMovieUseCaseTest.java
package com.movierama.application.usecase;

import com.movierama.domain.model.Movie;
import com.movierama.domain.model.User;
import com.movierama.domain.model.Vote;
import com.movierama.domain.model.VoteType;
import com.movierama.domain.repository.MovieRepository;
import com.movierama.domain.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class VoteForMovieUseCaseTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private VoteRepository voteRepository;

    private VoteForMovieUseCase voteForMovieUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        voteForMovieUseCase = new VoteForMovieUseCase(movieRepository, voteRepository);
    }

    @Test
    void castVote_NewVote_Succeeds() {
        // Arrange
        User submitter = new User(2L, "submitter", "password", "submitter@example.com");
        User voter = new User(1L, "voter", "password", "voter@example.com");
        Movie movie = new Movie(1L, "Test Movie", "Description", submitter, LocalDateTime.now());
        Vote vote = new Vote(null, voter, movie, VoteType.LIKE);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(voteRepository.findByUserIdAndMovieId(1L, 1L)).thenReturn(Optional.empty());
        when(voteRepository.save(any(Vote.class))).thenReturn(vote);

        when(voteRepository.countByMovieIdAndVoteType(1L, VoteType.LIKE)).thenReturn(1);
        when(voteRepository.countByMovieIdAndVoteType(1L, VoteType.HATE)).thenReturn(0);

        // Act
        Map<String, Integer> result = voteForMovieUseCase.castVote(vote);

        // Assert
        verify(voteRepository, times(1)).save(vote);
        assertEquals(1, result.get("likeCount"));
        assertEquals(0, result.get("hateCount"));
    }

    @Test
    void castVote_ForOwnMovie_ThrowsException() {
        // Arrange
        User user = new User(1L, "user", "password", "user@example.com");
        Movie movie = new Movie(1L, "Test Movie", "Description", user, LocalDateTime.now());
        Vote vote = new Vote(null, user, movie, VoteType.LIKE);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> voteForMovieUseCase.castVote(vote));
        verify(voteRepository, never()).save(any());
    }

    @Test
    void castVote_MovieNotFound_ThrowsException() {
        // Arrange
        User user = new User(1L, "user", "password", "user@example.com");
        Movie movie = new Movie(1L, "Test Movie", "Description", new User(2L), LocalDateTime.now());
        Vote vote = new Vote(null, user, movie, VoteType.LIKE);

        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> voteForMovieUseCase.castVote(vote));
        verify(voteRepository, never()).save(any());
    }

    @Test
    void castVote_ChangeExistingVote_Succeeds() {
        // Arrange
        User submitter = new User(2L, "submitter", "password", "submitter@example.com");
        User voter = new User(1L, "voter", "password", "voter@example.com");
        Movie movie = new Movie(1L, "Test Movie", "Description", submitter, LocalDateTime.now());
        Vote existingVote = new Vote(1L, voter, movie, VoteType.HATE);
        Vote newVote = new Vote(null, voter, movie, VoteType.LIKE);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(voteRepository.findByUserIdAndMovieId(1L, 1L)).thenReturn(Optional.of(existingVote));
        when(voteRepository.save(any(Vote.class))).thenReturn(newVote);

        when(voteRepository.countByMovieIdAndVoteType(1L, VoteType.LIKE)).thenReturn(1);
        when(voteRepository.countByMovieIdAndVoteType(1L, VoteType.HATE)).thenReturn(0);

        // Act
        Map<String, Integer> result = voteForMovieUseCase.castVote(newVote);

        // Assert
        verify(voteRepository, times(1)).save(newVote);
        assertEquals(1, result.get("likeCount"));
        assertEquals(0, result.get("hateCount"));
    }
}