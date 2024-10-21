package com.keduit.show.repository;

import com.keduit.show.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    // 특정 회원의 즐겨찾기 특정 공연 조회
    @Query("select f from Favorite f where f.member.num = :memberNum and f.showing.mt20id = :mt20id")
    Optional<Favorite> findByIds(@Param("memberNum") Long memberNum, @Param("mt20id") String mt20id);

    //특정회원의 즐겨찾기 리스트
    @Query("select f from Favorite f where f.member.num = :memberNum")
    List<Favorite> findReviewByMemberNum(@Param("memberNum") Long memberNum);

    //하나의 즐겨찾기 삭제
    @Modifying
    @Transactional
    @Query("delete from Favorite f where f.num = :favoriteNum")
    void remove(@Param("favoriteNum") Long favoriteNum);

}
