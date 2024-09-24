package com.movierama.presentation.controller;

import com.movierama.application.usecase.VoteForMovieUseCase;
import com.movierama.domain.model.User;
import com.movierama.domain.model.Movie;
import com.movierama.domain.model.Vote;
import com.movierama.domain.repository.MovieRepository;
import com.movierama.presentation.dto.VoteRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/votes")
@Tag(name = "Votes", description = "Vote management endpoints")
public class VoteController {

    private final VoteForMovieUseCase voteForMovieUseCase;
    private final MovieRepository movieRepository;

    public VoteController(VoteForMovieUseCase voteForMovieUseCase, MovieRepository movieRepository) {
        this.voteForMovieUseCase = voteForMovieUseCase;
        this.movieRepository = movieRepository;
    }

    @PostMapping
    @Operation(summary = "Cast a vote", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Map<String, Object>> castVote(@Valid @RequestBody VoteRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        Vote vote = new Vote(null, user, movie, request.getVoteType());

        try {
            Map<String, Integer> voteCounts = voteForMovieUseCase.castVote(vote);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "likeCount", voteCounts.get("likeCount"),
                    "hateCount", voteCounts.get("hateCount")
            ));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

