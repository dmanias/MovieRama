package com.movierama.presentation.dto;

import com.movierama.domain.model.VoteType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequest {

    @NotNull(message = "Movie ID is required")
    private Long movieId;

    @NotNull(message = "Vote type is required")
    private VoteType voteType;

    // Constructors
    public VoteRequest() {}

    public VoteRequest(Long movieId, VoteType voteType) {
        this.movieId = movieId;
        this.voteType = voteType;
    }

    // toString method for logging
    @Override
    public String toString() {
        return "VoteRequest{" +
                "movieId=" + movieId +
                ", voteType=" + voteType +
                '}';
    }
}