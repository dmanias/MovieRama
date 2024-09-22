package com.movierama.domain.repository;

import com.movierama.domain.model.Movie;
import com.movierama.domain.model.User;
import com.movierama.domain.model.Vote;
import com.movierama.domain.model.VoteType;
import java.util.Optional;

/**
 * Repository interface for Vote entity.
 */
public interface VoteRepository {
    /**
     * Saves the given vote.
     *
     * @param vote the vote to save (must not be null)
     * @return the saved vote
     */
    Vote save(Vote vote);

    /**
     * Finds a vote by user ID and movie ID.
     *
     * @param userId the ID of the user
     * @param movieId the ID of the movie
     * @return an Optional containing the found vote, or empty if not found
     */
    Optional<Vote> findByUserIdAndMovieId(Long userId, Long movieId);
    Optional<Vote> findByUserAndMovie(User user, Movie movie);
    /**
     * Counts the number of votes of a specific type for a movie.
     *
     * @param movieId the ID of the movie
     * @param voteType the type of vote to count
     * @return the number of votes
     */
    int countByMovieIdAndVoteType(Long movieId, VoteType voteType);

    /**
     * Deletes a vote.
     *
     * @param vote the vote to delete
     */
    void delete(Vote vote);
}