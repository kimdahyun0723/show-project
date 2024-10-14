package com.keduit.show.repository;

import com.keduit.show.entity.Showing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ShowRepository extends JpaRepository<Showing, String> ,
        QuerydslPredicateExecutor<Showing>, ShowRepositoryCustom {

    Page<Showing> findAll(Pageable pageable);

    //공연아이디 존재 여부
    boolean existsByMt20id(String mt20id);

    //공연 종료일이 현재 시간 기준으로 일주일이 지난 공연아이디 반환
    @Query("SELECT s.mt20id FROM Showing s WHERE s.prfpdto <= :date")
    List<String> findMt20idByPrfpdtoBefore(@Param("date") LocalDate date);

    //공연시설 아이디 전체 조회
    @Query(value = "select distinct show_mt10id from showing", nativeQuery = true)
    List<String> findMt10idAll();

    //공연 아이디를 받아 공연시설 아이디 반환
    @Query("select s.mt10id from Showing s where s.mt20id = :mt20id")
    String findMt10idByMt20id(@Param("mt20id") String mt20id);

}
