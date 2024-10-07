package com.keduit.show.entity;

import javax.persistence.*;

public class MemberImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_img_num")
    private Long num;

    private String imgName;        // 이미지 파일명

    private String oriImgName;      // 원본 이미지 이름

    private String imgUrl;      // 이미지 조회 경로

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_num")
    private Member member;

    public void updateMemberImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
