package com.keduit.show.service;

import com.keduit.show.dto.BoardDTO;
import com.keduit.show.dto.BoardSearchDTO;
import com.keduit.show.entity.Board;
import com.keduit.show.entity.Member;
import com.keduit.show.repository.BoardRepository;
import com.keduit.show.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Page<Board> getBoardsPage(BoardSearchDTO boardSearchDTO, Pageable pageable) {
        return boardRepository.getBoardsPage(boardSearchDTO, pageable);
    }


    public void saveBoard(BoardDTO boardDTO, String Id) {
        Member member = memberRepository.findById(Id);
        Board board = boardDTO.createBoard(member);
        boardRepository.save(board);
    }

    public Board getBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }


    public BoardDTO getBoardDtl(Long BoardId) {
        Board board = boardRepository.findById(BoardId).orElseThrow(EntityNotFoundException::new);

        return BoardDTO.of(board);
    }

    public boolean validationBoard(Long BoardId, String Id) {
        Member member = memberRepository.findById(Id);
        Board board = boardRepository.findById(BoardId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = board.getMember();
        System.out.println("===================================" + savedMember.getId());
        System.out.println("===================================" + member.getId());

        if (!StringUtils.equals(savedMember.getId(), member.getId())) {
            return false;
        }
        return true;
    }

    public void deleteBoard(Long BoardId) {
        Board board = boardRepository.findById(BoardId).orElseThrow(EntityNotFoundException::new);
        boardRepository.delete(board);
    }

    public void updateBoard(BoardDTO boardDTO, Long BoardId) {
        Board board = boardRepository.findById(BoardId).orElseThrow(EntityNotFoundException::new);
        board.updateBoard(boardDTO);


    }
}
