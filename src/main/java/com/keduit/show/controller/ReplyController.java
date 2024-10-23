package com.keduit.show.controller;

import com.keduit.show.dto.ReplyRequestDTO;
import com.keduit.show.dto.ReplyResponseDTO;
import com.keduit.show.entity.Reply;
import com.keduit.show.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/addReply/{id}/reply")
    @ResponseBody
    public ResponseEntity<ReplyResponseDTO> writeComment(@PathVariable Long id, @RequestBody ReplyRequestDTO replyRequestDTO, Principal principal) {

        try {
        Reply savedReply = replyService.writeComment(replyRequestDTO, id, principal.getName());

        // 생성된 댓글 정보를 DTO로 변환
        ReplyResponseDTO response = new ReplyResponseDTO(savedReply);

        return ResponseEntity.ok(response); // JSON 응답 반환
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/board/{id}/reply/{replyId}/update")
    @ResponseBody
    public ResponseEntity<ReplyResponseDTO> updateComment(@PathVariable Long id, @PathVariable Long replyId, @RequestBody ReplyRequestDTO replyRequestDTO) {
        Reply updatedReply = replyService.updateComment(replyRequestDTO, replyId); // 댓글 수정 서비스 호출

        // 수정된 댓글 정보를 담은 DTO 생성
        ReplyResponseDTO response = new ReplyResponseDTO(updatedReply);

        return ResponseEntity.ok(response); // 수정된 댓글 정보를 JSON 형식으로 반환
    }


    @DeleteMapping("/board/{id}/reply/{replyId}/remove")
    @ResponseBody
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @PathVariable Long replyId) {
        replyService.deleteComment(replyId);
        return ResponseEntity.ok().build(); // 성공 시 200 OK 응답
    }


}
