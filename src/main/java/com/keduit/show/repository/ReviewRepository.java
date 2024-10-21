package com.keduit.show.repository;

import com.keduit.show.entity.Review;
import com.keduit.show.entity.Showing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>,
        QuerydslPredicateExecutor<Review>, ReviewRepositoryCustom {

    //특정 공연의 리뷰 리스트 조회
    List<Review> findByShowing(Showing showing);

    //특정 회원의 리뷰 리스트 조회
    @Query("select r from Review r where r.member.num = :memberNum")
    List<Review> findByMember(@Param("memberNum") Long memberNum);

    //리뷰 번호를 기준으로 공연 이름 조회
    @Query("select r.showing.prfnm from Review r where r.num = :num")
    String findPrfnmByReviewNum(@Param("num") Long num);
}
