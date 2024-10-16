package com.keduit.show.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_num")
    private Long num;

    @Column
    private Integer rating; //평점(1~10) 가능하면 별표시 해보기

    @Column
    private String content; //내용

    //공연하나에 여러 리뷰
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mt20id")
    private Showing showing; //공연

    //회원 한명이 여러 리뷰
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_num")
    private Member member; //작성자

    //댓글 내용, 별점만 수정 가능
    public void update(Integer reting, String content) {
        this.rating = reting;
        this.content = content;
    }

}
