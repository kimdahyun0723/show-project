package com.keduit.show.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_num")
    private Long num;

    @Column
    private Integer rating; //평점(1~10) 가능하면 별표시 해보기

    @Column
    private String content; //내용

    @Column
    @CreatedDate
    private String createDate; //작성일

    @Column
    @LastModifiedDate
    private String modifyDate; //수정일

    //회원 한명이 여러 리뷰
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_num")
    private Member member; //회원

    //공연하나에 여러 리뷰
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mt20id")
    private Showing showing; //공연

    //댓글 내용, 별점만 수정 가능
    public void update(Integer rating, String content) {
        this.rating = rating;
        this.content = content;
    }

}
