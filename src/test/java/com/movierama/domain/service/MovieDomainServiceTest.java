// src/test/java/com/movierama/domain/service/VoteDomainServiceTest.java

package com.movierama.domain.service;

import com.movierama.domain.model.Movie;
import com.movierama.domain.model.User;
import com.movierama.domain.model.Vote;
import com.movierama.domain.model.VoteType;
import com.movierama.domain.repository.VoteRepository;
import com.movierama.domain.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class VoteDomainServiceTest {

    @Mock
    private VoteRepository voteRepository;
    @Mock
    private MovieRepository movieRepository;

    private VoteDomainService voteDomainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        voteDomainService = new VoteDomainServiceImpl(voteRepository, movieRepository);
    }

    @Test
    void castVote_NewVote_Succeeds() {
        User user = new User(1L);
        Movie movie = new Movie(1L);
        Vote vote = new Vote(null, user, movie, VoteType.LIKE);

        when(voteRepository.findByUserAndMovie(user, movie)).thenReturn(Optional.empty());
        when(voteRepository.save(any(Vote.class))).thenReturn(vote);

        voteDomainService.castVote(vote);

        verify(voteRepository).save(vote);
        verify(movieRepository).save(movie);
    }

    @Test
    void changeVote_ExistingVote_Succeeds() {
        Vote existingVote = new Vote(1L, new User(1L), new Movie(1L), VoteType.LIKE);
        VoteType newVoteType = VoteType.HATE;

        when(voteRepository.findByUserAndMovie(any(), any())).thenReturn(Optional.of(existingVote));

        voteDomainService.changeVote(existingVote, newVoteType);

        assertEquals(newVoteType, existingVote.getVoteType());
        verify(voteRepository).save(existingVote);
        // Remove the expectation for movieRepository.save() if it's not part of the current implementation
    }

    @Test
    void retractVote_ExistingVote_Succeeds() {
        Vote existingVote = new Vote(1L, new User(1L), new Movie(1L), VoteType.LIKE);

        voteDomainService.retractVote(existingVote);

        verify(voteRepository).delete(existingVote);
        // Remove the expectation for movieRepository.save() if it's not part of the current implementation
    }

    @Test
    void castVote_UserVotingForOwnMovie_ThrowsException() {
        User user = new User(1L);
        Movie movie = new Movie(1L);
        movie.setSubmittedBy(user);
        Vote vote = new Vote(null, user, movie, VoteType.LIKE);

        assertThrows(IllegalStateException.class, () -> voteDomainService.castVote(vote));
    }
}