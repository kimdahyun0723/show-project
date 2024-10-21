package com.keduit.show.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.keduit.show.entity.Review;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewResponseDTO {

    //리뷰 요청에 따른 반환

    private Long num;

    private Integer rating;
    private String content;

    private String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    private String modifyDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

    private String memberId; //작성자 아이디
    private String mt20id; //공연 아이디

    //entity -> dto
    //테이블에서 리뷰 데이터를 가져와 dto 로 화면에 전달
    public static ReviewResponseDTO toDTO(Review review) {
        return new ReviewResponseDTO(
                review.getNum(),
                review.getRating(),
                review.getContent(),
                review.getCreateDate(),
                review.getModifyDate(),
                review.getMember().getId(),
                review.getShowing().getMt20id()
        );
    }

    //회원아이디 첫글자만 보이게
    public String getObfuscatedMemberName() {
        String memberId = this.memberId; // 실제 memberId 가져오기
        if (memberId.length() <= 1) {
            return memberId;  // 이름이 1글자라면 그대로 반환
        }
        return memberId.substring(0, 1) + "*".repeat(memberId.length() - 1);
    }
}
