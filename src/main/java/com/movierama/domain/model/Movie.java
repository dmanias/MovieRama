package com.movierama.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "movies")
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User submittedBy;

    @Column(nullable = false)
    private LocalDateTime publicationDate;

    @Column(nullable = false)
    private int likeCount = 0;

    @Column(nullable = false)
    private int hateCount = 0;

    // Default constructor
    public Movie() {}

    // Constructor for voting (only requires ID)
    public Movie(Long id) {
        this.id = id;
    }

    public Movie(Long id, String title, String description, User submittedBy, LocalDateTime publicationDate) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }
        if (submittedBy == null) {
            throw new IllegalArgumentException("SubmittedBy (User) cannot be null.");
        }
        if (publicationDate == null) {
            throw new IllegalArgumentException("Publication date cannot be null.");
        }

        this.id = id;
        this.title = title;
        this.description = description;
        this.submittedBy = submittedBy;
        this.publicationDate = publicationDate;
        this.likeCount = 0;
        this.hateCount = 0;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = Math.max(0, likeCount);
    }

    public void setHateCount(int hateCount) {
        this.hateCount = Math.max(0, hateCount);
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        this.likeCount = Math.max(0, this.likeCount - 1);
    }

    public void incrementHateCount() {
        this.hateCount++;
    }

    public void decrementHateCount() {
        this.hateCount = Math.max(0, this.hateCount - 1);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", submittedBy=" + (submittedBy != null ? submittedBy.getUsername() : "null") +
                ", publicationDate=" + publicationDate +
                ", likeCount=" + likeCount +
                ", hateCount=" + hateCount +
                '}';
    }
}