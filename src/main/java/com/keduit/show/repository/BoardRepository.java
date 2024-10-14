package com.keduit.show.repository;

import com.keduit.show.entity.Board;
import com.keduit.show.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    List<Board> findByMember(Member member);
}
