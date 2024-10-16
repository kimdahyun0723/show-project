package com.keduit.show.dto;

import com.keduit.show.entity.Board;
import com.keduit.show.entity.Member;
import com.keduit.show.entity.Review;
import com.keduit.show.entity.Showing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewRequestDTO {

    private Long num;

    private Integer rating; //평점(1~10) 가능하면 별표시 해보기

    private String content; //내용

    private String showing; //공연

    private String createBy; //작성자아이디

    //작성시간, 수정시간, 작성자(member) 는 baseEntity 에서 상속

    // DTO -> Entity
    public Review toEntity(Showing showing, Member member) {
        return Review.builder()
                .rating(rating)
                .content(content)
                .showing(showing)
                .member(member)
                .build();
    }
}
