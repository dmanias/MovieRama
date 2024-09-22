package com.movierama.infrastructure.repository;

import com.movierama.domain.model.Movie;
import com.movierama.domain.model.User;
import com.movierama.domain.model.Vote;
import com.movierama.domain.model.VoteType;
import com.movierama.domain.repository.VoteRepository;
import com.movierama.infrastructure.entity.VoteEntity;
import com.movierama.infrastructure.mapper.VoteEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class VoteRepositoryImpl implements VoteRepository {

    private final VoteJpaRepository voteJpaRepository;
    private final VoteEntityMapper voteEntityMapper;

    public VoteRepositoryImpl(VoteJpaRepository voteJpaRepository, VoteEntityMapper voteEntityMapper) {
        this.voteJpaRepository = voteJpaRepository;
        this.voteEntityMapper = voteEntityMapper;
    }

    @Override
    public Vote save(Vote vote) {
        VoteEntity voteEntity = voteEntityMapper.toEntity(vote);
        VoteEntity savedEntity = voteJpaRepository.save(voteEntity);
        return voteEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Vote> findByUserIdAndMovieId(Long userId, Long movieId) {
        return voteJpaRepository.findByUserIdAndMovieId(userId, movieId)
                .map(voteEntityMapper::toDomain);
    }

    @Override
    public Optional<Vote> findByUserAndMovie(User user, Movie movie) {
        return voteJpaRepository.findByUserIdAndMovieId(user.getId(), movie.getId())
                .map(voteEntityMapper::toDomain);
    }

    @Override
    public int countByMovieIdAndVoteType(Long movieId, VoteType voteType) {
        return voteJpaRepository.countByMovieIdAndVoteType(movieId, voteType);
    }

    @Override
    public void delete(Vote vote) {
        VoteEntity voteEntity = voteEntityMapper.toEntity(vote);
        voteJpaRepository.delete(voteEntity);
    }
}