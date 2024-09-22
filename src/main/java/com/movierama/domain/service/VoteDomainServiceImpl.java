package com.movierama.domain.service;

import com.movierama.domain.model.Vote;
import com.movierama.domain.model.VoteType;
import com.movierama.domain.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteDomainServiceImpl implements VoteDomainService {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteDomainServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public void castVote(Vote vote) {
        if (vote == null) {
            throw new IllegalArgumentException("Vote cannot be null.");
        }
        voteRepository.save(vote);
    }

    @Override
    public void changeVote(Vote vote, VoteType newVoteType) {
        if (vote == null || newVoteType == null) {
            throw new IllegalArgumentException("Vote and newVoteType cannot be null.");
        }
        vote.setVoteType(newVoteType);
        voteRepository.save(vote);
    }

    @Override
    public void retractVote(Vote vote) {
        if (vote == null) {
            throw new IllegalArgumentException("Vote cannot be null.");
        }
        voteRepository.delete(vote);
    }

    @Override
    public Optional<Vote> getVote(Long userId, Long movieId) {
        return voteRepository.findByUserIdAndMovieId(userId, movieId);
    }

    @Override
    public int getVoteCount(Long movieId, VoteType voteType) {
        return voteRepository.countByMovieIdAndVoteType(movieId, voteType);
    }
}