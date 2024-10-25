package com.keduit.show.repository;

import com.keduit.show.dto.BoardSearchDTO;
import com.keduit.show.entity.Board;
import com.keduit.show.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {


    Page<Board> getBoardsPage(BoardSearchDTO boardSearchDTO, Pageable pageable);

    Page<Board> getBoardsPageWithMember(BoardSearchDTO boardSearchDTO, Pageable pageable, Long BoardNum);
}
