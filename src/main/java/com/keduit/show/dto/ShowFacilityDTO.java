package com.keduit.show.dto;

import com.keduit.show.entity.ShowFacility;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@Builder
public class ShowFacilityDTO {

    private String mt10id; //공연시설 아이디(pk)

    private String adres; //공연시설 주소

    private Double la; //위도

    private Double lo; //경도

    //엔티티를 dto 로 변환
    public static ShowFacilityDTO toDTO(ShowFacility showFacility) {
        return new ShowFacilityDTO(
                showFacility.getMt10id(),
                showFacility.getAdres(),
                showFacility.getLa(),
                showFacility.getLo()
        );
    }
}
