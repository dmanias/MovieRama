package com.movierama.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity submittedBy;

    @Column(nullable = false)
    private LocalDateTime publicationDate;

    @Column(nullable = false)
    private int likeCount = 0;

    @Column(nullable = false)
    private int hateCount = 0;

    // Default constructor
    public MovieEntity() {}

    // Constructor with all fields
    public MovieEntity(Long id, String title, String description, UserEntity submittedBy, LocalDateTime publicationDate, int likeCount, int hateCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.submittedBy = submittedBy;
        this.publicationDate = publicationDate;
        this.likeCount = likeCount;
        this.hateCount = hateCount;
    }

    // Increment and decrement methods for like and hate counts
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

    // toString method for logging and debugging
    @Override
    public String toString() {
        return "MovieEntity{" +
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