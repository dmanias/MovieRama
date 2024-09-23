package com.movierama.domain.service;

import com.movierama.domain.model.Movie;
import com.movierama.domain.model.Vote;
import com.movierama.domain.model.VoteType;
import com.movierama.domain.repository.MovieRepository;
import com.movierama.domain.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteDomainServiceImpl implements VoteDomainService {

    private final VoteRepository voteRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public VoteDomainServiceImpl(VoteRepository voteRepository, MovieRepository movieRepository) {
        this.voteRepository = voteRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void castVote(Vote vote) {
        if (vote.getUser().equals(vote.getMovie().getSubmittedBy())) {
            throw new IllegalStateException("Users cannot vote for their own movies");
        }

        voteRepository.save(vote);
        updateMovieVoteCount(vote.getMovie(), vote.getVoteType());
    }

    private void updateMovieVoteCount(Movie movie, VoteType voteType) {
        if (voteType == VoteType.LIKE) {
            movie.incrementLikeCount();
        } else {
            movie.incrementHateCount();
        }
        movieRepository.save(movie);
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