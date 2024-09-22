package com.movierama.presentation.mapper;

import com.movierama.domain.model.Movie;
import com.movierama.domain.model.User;
import com.movierama.domain.model.Vote;
import com.movierama.presentation.dto.VoteRequest;
import org.springframework.stereotype.Component;

@Component
public class VoteDtoMapper {

    public Vote toDomain(VoteRequest dto, Long userId) {
        return new Vote(
                null, // id will be generated
                new User(userId),
                new Movie(dto.getMovieId()),
                dto.getVoteType()
        );
    }

    // You might want to add a method to convert from Domain to DTO if needed for responses
    // public VoteResponseDto toDto(Vote vote) { ... }
}