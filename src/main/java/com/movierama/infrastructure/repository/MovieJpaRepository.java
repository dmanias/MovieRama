package com.movierama.infrastructure.repository;

import com.movierama.infrastructure.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieJpaRepository extends JpaRepository<MovieEntity, Long> {

    List<MovieEntity> findBySubmittedById(Long userId);

    @Query("SELECT m FROM MovieEntity m ORDER BY m.likeCount DESC")
    List<MovieEntity> findAllOrderByLikesDesc();

    @Query("SELECT m FROM MovieEntity m ORDER BY m.hateCount DESC")
    List<MovieEntity> findAllOrderByHatesDesc();

    @Query("SELECT m FROM MovieEntity m ORDER BY m.publicationDate DESC")
    List<MovieEntity> findAllOrderByPublicationDateDesc();
}