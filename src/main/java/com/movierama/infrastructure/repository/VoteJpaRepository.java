package com.movierama.infrastructure.repository;

import com.movierama.infrastructure.entity.VoteEntity;
import com.movierama.domain.model.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteJpaRepository extends JpaRepository<VoteEntity, Long> {

    Optional<VoteEntity> findByUserIdAndMovieId(Long userId, Long movieId);

    int countByMovieIdAndVoteType(Long movieId, VoteType voteType);
}