// src/test/java/com/movierama/presentation/controller/VoteControllerTest.java

package com.movierama.presentation.controller;

import com.movierama.application.usecase.VoteForMovieUseCase;
import com.movierama.domain.model.Movie;
import com.movierama.domain.model.User;
import com.movierama.domain.model.Vote;
import com.movierama.domain.model.VoteType;
import com.movierama.domain.repository.MovieRepository;
import com.movierama.presentation.dto.VoteRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class VoteControllerTest {

    @Mock
    private VoteForMovieUseCase voteForMovieUseCase;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private Authentication authentication;

    private VoteController voteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        voteController = new VoteController(voteForMovieUseCase, movieRepository);
    }

    @Test
    void castVote_ValidVote_ReturnsSuccessResponse() {
        VoteRequest request = new VoteRequest(1L, VoteType.LIKE);
        User user = new User(1L);
        Movie movie = new Movie(1L);

        when(authentication.getPrincipal()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(voteForMovieUseCase.castVote(any(Vote.class))).thenReturn(Map.of("likeCount", 1, "hateCount", 0));

        ResponseEntity<Map<String, Object>> response = voteController.castVote(request, authentication);

        assertTrue((Boolean) response.getBody().get("success"));
        assertEquals(1, response.getBody().get("likeCount"));
        assertEquals(0, response.getBody().get("hateCount"));
    }

    @Test
    void castVote_MovieNotFound_ReturnsNotFoundResponse() {
        VoteRequest request = new VoteRequest(1L, VoteType.LIKE);
        User user = new User(1L);

        when(authentication.getPrincipal()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Map<String, Object>> response = voteController.castVote(request, authentication);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void castVote_IllegalStateException_ReturnsBadRequestResponse() {
        VoteRequest request = new VoteRequest(1L, VoteType.LIKE);
        User user = new User(1L);
        Movie movie = new Movie(1L);

        when(authentication.getPrincipal()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(voteForMovieUseCase.castVote(any(Vote.class))).thenThrow(new IllegalStateException("Cannot vote for own movie"));

        ResponseEntity<Map<String, Object>> response = voteController.castVote(request, authentication);

        assertFalse((Boolean) response.getBody().get("success"));
        assertEquals("Cannot vote for own movie", response.getBody().get("message"));
    }
}