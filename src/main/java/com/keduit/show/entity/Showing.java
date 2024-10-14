package com.keduit.show.entity;

import com.keduit.show.constant.Genre;
import com.keduit.show.constant.Location;
import com.keduit.show.constant.State;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Showing {

    @Id
    private String mt20id; //공연아이디(pk)

    @Column
    private String prfnm; //공연이름(목록)
    @Column
    private LocalDate prfpdfrom; //공연시작일(목록)
    @Column
    private LocalDate prfpdto; //공연종료일(목록)
    @Column
    private String fcltynm; //공연시설명(목록)

    @Column
    private String prfcast; //출연진
    @Column
    private String prfcrew; //제작진

    @Column
    private String prfruntime; //관람시간
    @Column
    private String prfage; //관람연령

    @Column
    private String entrpsnm; //기획제작사
    @Column
    private String entrpsnmH; //주최

    @Column
    private Integer pcseguidance; //가격

    @Column
    private String poster; //포스터(목록)

    @Column
    @Enumerated(EnumType.STRING)
    private Location area; //지역(시도)

    @Column
    @Enumerated(EnumType.STRING)
    private Genre genrenm; //장르

    @Column
    @Enumerated(EnumType.STRING)
    private State prfstate; //공연상태

    @Column
    private String styurl; //소개이미지

    @Column(name="show_mt10id")
    private String mt10id; //공연시설아이디 -> 시설상세

    @Column
    @ColumnDefault("0")
    private Integer likeCount; //좋아요수(기본값0)

}
