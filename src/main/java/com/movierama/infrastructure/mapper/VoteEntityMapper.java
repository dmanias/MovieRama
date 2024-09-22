package com.movierama.infrastructure.mapper;

import com.movierama.domain.model.Movie;
import com.movierama.domain.model.User;
import com.movierama.domain.model.Vote;
import com.movierama.infrastructure.entity.VoteEntity;
import org.springframework.stereotype.Component;

@Component
public class VoteEntityMapper {

    public VoteEntity toEntity(Vote vote) {
        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setId(vote.getId());
        voteEntity.setUser(new UserEntityMapper().toEntity(vote.getUser()));
        voteEntity.setMovie(new MovieEntityMapper().toEntity(vote.getMovie()));
        voteEntity.setVoteType(vote.getVoteType());
        return voteEntity;
    }

    public Vote toDomain(VoteEntity voteEntity) {
        return new Vote(
                voteEntity.getId(),
                new User(voteEntity.getUser().getId()),
                new Movie(voteEntity.getMovie().getId()),
                voteEntity.getVoteType()
        );
    }
}