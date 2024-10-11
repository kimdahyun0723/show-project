package com.keduit.show.repository;

import com.keduit.show.entity.Board;
import com.keduit.show.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByBoard(Board board);
}
