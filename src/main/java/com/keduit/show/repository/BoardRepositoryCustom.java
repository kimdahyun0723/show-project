package com.keduit.show.repository;

import com.keduit.show.dto.BoardSearchDTO;
import com.keduit.show.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    Page<Board> getBoardsPage(BoardSearchDTO boardSearchDTO, Pageable pageable);
}
