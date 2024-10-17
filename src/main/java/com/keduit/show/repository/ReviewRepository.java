package com.keduit.show.repository;

import com.keduit.show.entity.Review;
import com.keduit.show.entity.Showing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //특정 공연의 댓글 리스트 조회
//    @Query("select r from Review r where r.showing.mt20id = :mt20id")
//    List<Review> findByMt20id(@Param("mt20id") String mt20id);

    List<Review> findByShowing(Showing showing);

}
