package com.keduit.show.service;


import com.keduit.show.dto.ReplyRequestDTO;
import com.keduit.show.dto.ReplyResponseDTO;
import com.keduit.show.entity.Board;
import com.keduit.show.entity.Member;
import com.keduit.show.entity.Reply;
import com.keduit.show.repository.BoardRepository;
import com.keduit.show.repository.CommentRepository;
import com.keduit.show.repository.MemberRepository;
import com.keduit.show.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Override
    public Long writeComment(ReplyRequestDTO replyRequestDTO, Long boardId, String id) {
        Member member = memberRepository.findById(id);
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        Reply result = Reply.builder()
                .reply(replyRequestDTO.getReply())
                .board(board)
                .member(member)
                .build();
        replyRepository.save(result);

        return result.getNum();
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
    public void updateComment(ReplyRequestDTO commentRequestDTO, Long commentId) {
        Reply reply = replyRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        reply.update(commentRequestDTO.getReply());
        replyRepository.save(reply);
    }

    @Override
    public void deleteComment(Long commentId) {
        replyRepository.deleteById(commentId);
    }
}
