package com.movierama.application.usecase;

import com.movierama.domain.model.Movie;
import com.movierama.domain.model.User;
import com.movierama.domain.model.Vote;
import com.movierama.domain.model.VoteType;
import com.movierama.domain.repository.MovieRepository;
import com.movierama.domain.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class VoteForMovieUseCase {

    private final MovieRepository movieRepository;
    private final VoteRepository voteRepository;

    public VoteForMovieUseCase(MovieRepository movieRepository, VoteRepository voteRepository) {
        this.movieRepository = movieRepository;
        this.voteRepository = voteRepository;
    }

    @Transactional
    public Map<String, Integer> castVote(Vote vote) {
        Movie movie = movieRepository.findById(vote.getMovie().getId())
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));
        User user = vote.getUser();

        if (movie.getSubmittedBy().getId().equals(user.getId())) {
            throw new IllegalStateException("Users cannot vote on their own movies");
        }

        Optional<Vote> existingVote = voteRepository.findByUserAndMovie(user, movie);

        if (existingVote.isPresent()) {
            Vote currentVote = existingVote.get();
            if (currentVote.getVoteType() == vote.getVoteType()) {
                // User is clicking the same vote type, so remove the vote
                voteRepository.delete(currentVote);
                updateMovieVoteCounts(movie, currentVote.getVoteType(), false);
            } else {
                // User is changing their vote
                updateMovieVoteCounts(movie, currentVote.getVoteType(), false);
                currentVote.setVoteType(vote.getVoteType());
                voteRepository.save(currentVote);
                updateMovieVoteCounts(movie, vote.getVoteType(), true);
            }
        } else {
            // New vote
            voteRepository.save(vote);
            updateMovieVoteCounts(movie, vote.getVoteType(), true);
        }

        movieRepository.save(movie);

        return Map.of(
                "likeCount", movie.getLikeCount(),
                "hateCount", movie.getHateCount()
        );
    }

    private void updateMovieVoteCounts(Movie movie, VoteType voteType, boolean increment) {
        if (voteType == VoteType.LIKE) {
            if (increment) movie.incrementLikeCount(); else movie.decrementLikeCount();
        } else {
            if (increment) movie.incrementHateCount(); else movie.decrementHateCount();
        }
    }
}