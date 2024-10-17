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
}
