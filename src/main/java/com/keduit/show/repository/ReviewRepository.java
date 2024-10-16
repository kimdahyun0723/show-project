package com.keduit.show.repository;

import com.keduit.show.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //특정 공연의 특정 댓글 조회
    @Query("select r from Review r where r.showing.mt20id = :mt20id and r.num = :reviewNum")
    Optional<Review> findByShowingMt20idAndNum(@Param("mt20id") String mt20id,@Param("reviewNum") Long num);
}
