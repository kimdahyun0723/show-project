package com.keduit.show.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.keduit.show.entity.Reply;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class ReplyResponseDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm") // 날짜 형식 지정
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
    private Long num;
    private String reply;
    private String userId;
    private String email;
    private String imageUrl;

    @Builder
    public ReplyResponseDTO(Reply reply) {
        this.regTime = reply.getRegTime();
        this.updateTime = reply.getUpdateTime();
        this.num = reply.getNum();
        this.reply = reply.getReply();
        this.userId = reply.getMember().getId();
        this.email = reply.getMember().getEmail();
        this.imageUrl = reply.getMember().getMemberImg().getUrl();
    }

}
