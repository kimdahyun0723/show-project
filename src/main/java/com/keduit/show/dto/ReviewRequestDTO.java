package com.keduit.show.dto;

import com.keduit.show.entity.Board;
import com.keduit.show.entity.Member;
import com.keduit.show.entity.Review;
import com.keduit.show.entity.Showing;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReviewRequestDTO {

    //리뷰 추가 요청

    private Long num;

    private Integer rating; //평점(1~10) 가능하면 별표시 해보기
    private String content; //내용

    private String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    private String modifyDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

    private Member member; //회원
    private Showing showing; //공연

    // DTO -> Entity
    public Review toEntity() {
        Review reviews = Review.builder()
                .num(num)
                .rating(rating)
                .content(content)
                .createDate(createDate)
                .modifyDate(modifyDate)
                .member(member)
                .showing(showing)
                .build();
        return reviews;
    }
}
