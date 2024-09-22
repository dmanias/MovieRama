package com.movierama.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    private Long id;
    private User user;
    private Movie movie;
    private VoteType voteType;

    // Constructor for voting (only requires ID)
    public Vote(Long id) {
        this.id = id;
    }

    // Constructor without ID (useful for creating new votes)
    public Vote(User user, Movie movie, VoteType voteType) {
        this.user = user;
        this.movie = movie;
        this.voteType = voteType;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", user=" + (user != null ? user.getUsername() : "null") +
                ", movie=" + (movie != null ? movie.getTitle() : "null") +
                ", voteType=" + voteType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vote vote = (Vote) o;

        if (id != null ? !id.equals(vote.id) : vote.id != null) return false;
        if (user != null ? !user.equals(vote.user) : vote.user != null) return false;
        if (movie != null ? !movie.equals(vote.movie) : vote.movie != null) return false;
        return voteType == vote.voteType;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (movie != null ? movie.hashCode() : 0);
        result = 31 * result + (voteType != null ? voteType.hashCode() : 0);
        return result;
    }
}