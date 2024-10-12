package com.keduit.show.controller;

import com.keduit.show.dto.ReplyRequestDTO;
import com.keduit.show.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/board/{id}/reply")
    public String writeComment(@PathVariable Long id, ReplyRequestDTO replyRequestDTO, Principal principal) {
        replyService.writeComment(replyRequestDTO, id, principal.getName());

        return "redirect:/board/boardDtl/" + id;
    }

    @PostMapping("/board/{id}/reply/{replyId}/update")
    public String updateComment(@PathVariable Long id, @PathVariable Long replyId, ReplyRequestDTO replyRequestDTO) {
        replyService.updateComment(replyRequestDTO, replyId);
        return "redirect:/board/boardDtl/" + id;
    }

    /**
     * 댓글 삭제
     * @param id 게시물
     * @return 해당 게시물 리다이렉트
     */
    @GetMapping("/board/{id}/reply/{replyId}/remove")
    public String deleteComment(@PathVariable Long id, @PathVariable Long replyId) {
        replyService.deleteComment(replyId);
        return "redirect:/board/boardDtl/" + id;
    }


}
