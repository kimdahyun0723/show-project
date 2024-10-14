package com.keduit.show.entity;

import com.keduit.show.dto.ShowFacilityDTO;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ShowFacility {

    @Id
    @Column(name="show_facility_mt10id")
    private String mt10id; //공연시설 아이디(pk)

    @Column
    private String adres; //공연시설 주소

    @Column
    private Double la; //위도

    @Column
    private Double lo; //경도

    //dto 를 엔티티로 변경
    public static ShowFacility toEntity(ShowFacilityDTO showFacilityDTO) {
        return new ShowFacility(
                showFacilityDTO.getMt10id(),
                showFacilityDTO.getAdres(),
                showFacilityDTO.getLa(),
                showFacilityDTO.getLo()
        );
    }


}
