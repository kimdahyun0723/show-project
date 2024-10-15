package com.keduit.show.entity;

import com.keduit.show.constant.Genre;
import com.keduit.show.constant.Location;
import com.keduit.show.constant.State;
import com.keduit.show.constant.TicketStatus;
import com.keduit.show.exception.OutOfStockException;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Column
    @ColumnDefault("30")
    private Integer ticket; //공연당 티켓 수(무조건 30장)

    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public void removeTicket(int ticketNumber) {
        int restTicket = this.ticket - ticketNumber;
        if (restTicket < 0) {
            throw new OutOfStockException("티켓의 재고가 부족합니다.(현재 잔여 티켓 : " + this.ticket);
        }
        this.ticket = restTicket;
    }


    public void cancel(int cancelTicket) {
        this.ticket += cancelTicket;
    }
}
