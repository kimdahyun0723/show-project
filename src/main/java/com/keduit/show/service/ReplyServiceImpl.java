package com.keduit.show.service;


import com.keduit.show.dto.ReplyRequestDTO;
import com.keduit.show.dto.ReplyResponseDTO;
import com.keduit.show.entity.Board;
import com.keduit.show.entity.Member;
import com.keduit.show.entity.Reply;
import com.keduit.show.repository.BoardRepository;
import com.keduit.show.repository.MemberRepository;
import com.keduit.show.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    public Reply writeComment(ReplyRequestDTO replyRequestDTO, Long boardId, String userId) {
        Member member = memberRepository.findById(userId);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        Reply result = Reply.builder()
                .reply(replyRequestDTO.getReply())
                .board(board)
                .member(member)
                .build();

        replyRepository.save(result);

        return result; // 수정된 댓글 객체 반환
    }

    @Override
    public List<ReplyResponseDTO> commentList(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        List<Reply> comments = replyRepository.findByBoard(board);

        List<ReplyResponseDTO> list = comments.stream()
                .map(reply -> ReplyResponseDTO.builder()
                        .reply(reply)
                        .build())
                .collect(Collectors.toList());
        System.out.println();

        return list;
    }

    @Override
    public Reply updateComment(ReplyRequestDTO replyRequestDTO, Long replyId) {
        // 댓글 수정 로직 (예: 댓글을 찾고, 내용을 업데이트한 후 저장)
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        reply.update(replyRequestDTO.getReply()); // 댓글 내용 수정
        return replyRepository.save(reply); // 수정된 댓글 저장 및 반환
    }

    @Override
    public void deleteComment(Long commentId) {
        replyRepository.deleteById(commentId);
    }
}
