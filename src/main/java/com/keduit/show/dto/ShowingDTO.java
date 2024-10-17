package com.keduit.show.dto;

import com.keduit.show.constant.*;
import com.keduit.show.entity.Showing;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ShowingDTO {

    private String mt20id; //공연아이디(pk)

    private String prfnm; //공연이름
    private LocalDate prfpdfrom; //공연시작일
    private LocalDate prfpdto; //공연종료일
    private String fcltynm; //공연시설명

    private String prfcast; //출연진
    private String prfcrew; //제작진

    private String prfruntime; //관람시간
    private String prfage; //관람연령

    private String entrpsnm; //기획제작사
    private String entrpsnmH; //주최

    private Integer pcseguidance; //가격

    private String poster; //포스터

    private Location area; //지역(시도)

    private Genre genrenm; //장르

    private State prfstate; //공연상태

    private String styurl; //소개이미지

    private String mt10id; //공연시설아이디 -> 시설상세

    private Integer likeCount; //좋아요수
    
    private Integer ticket; // 티켓 갯수

    private TicketStatus ticketStatus;

    //리뷰 리스트
    private List<ReviewResponseDTO> reviews;

    private static ModelMapper modelMapper = new ModelMapper();

    //dto 받아서 entity 로 변환 -> db 에 저장할 때
    public Showing toEntity(){
        return modelMapper.map(this, Showing.class);
    }

    //entity 받아서 dto 로 변환 -> 화면에 뿌릴 때
    public static ShowingDTO toDTO(Showing show){
        return modelMapper.map(show, ShowingDTO.class);
    }
}

