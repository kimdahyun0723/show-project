package com.keduit.show.service;

import com.keduit.show.dto.ReplyRequestDTO;
import com.keduit.show.dto.ReplyResponseDTO;
import com.keduit.show.entity.Reply;


import java.util.List;

public interface ReplyService {

    /**
     * 댓글 작성

     */
    Reply writeComment(ReplyRequestDTO replyRequestDTO, Long boardId, String Id);

    /**
     * 댓글 조회
     */
    List<ReplyResponseDTO> commentList(Long id);

    /**
     * 댓글 수정
     */
    Reply updateComment(ReplyRequestDTO replyRequestDTO, Long commentId);

    /**
     * 댓글 삭제
     */
    void deleteComment(Long commentId);
}
