package com.movierama.domain.service;

import com.movierama.domain.model.Vote;
import com.movierama.domain.model.VoteType;
import java.util.Optional;

public interface VoteDomainService {
    /**
     * Casts a new vote.
     *
     * @param vote the vote to cast (must not be null)
     */
    void castVote(Vote vote);

    /**
     * Changes an existing vote to a new vote type.
     *
     * @param vote the existing vote (must not be null)
     * @param newVoteType the new vote type (must not be null)
     */
    void changeVote(Vote vote, VoteType newVoteType);

    /**
     * Retracts (deletes) an existing vote.
     *
     * @param vote the vote to retract (must not be null)
     */
    void retractVote(Vote vote);

    /**
     * Retrieves a vote by user ID and movie ID.
     *
     * @param userId the ID of the user
     * @param movieId the ID of the movie
     * @return an Optional containing the vote if found, or empty if not found
     */
    Optional<Vote> getVote(Long userId, Long movieId);

    /**
     * Counts the number of votes of a specific type for a movie.
     *
     * @param movieId the ID of the movie
     * @param voteType the type of vote to count
     * @return the number of votes
     */
    int getVoteCount(Long movieId, VoteType voteType);
}