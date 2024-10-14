package com.keduit.show.service;

import com.keduit.show.dto.*;
import com.keduit.show.entity.ShowFacility;
import com.keduit.show.entity.Showing;
import com.keduit.show.repository.ShowFacilityRepository;
import com.keduit.show.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShowService {

    //show 서비스

    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private ShowFacilityRepository showFacilityRepository;

    //전체 공연 리스트 조회
    public List<Showing> getShow(){
        return showRepository.findAll();
    }

    //필터, 페이지에 따른 공연 목록 조회
    public Page<Showing> getShowFilterPage(ShowSearchDTO searchDTO, Pageable pageable){
        return showRepository.getShowFilterPage(searchDTO, pageable);
    }

    //공연 아이디에 따른 공연목록 하나 조회
    public ShowingDTO getShowById(String showId){
        Showing show = showRepository.findById(showId).orElse(null);
        ShowingDTO showDTO = ShowingDTO.toDTO(show);
        return showDTO;
    }

    //시설 아이디에 따른 시설상세조회
    public ShowFacilityDTO getShowFacilityById(String detailId){
        String facilityId = showRepository.findMt10idByMt20id(detailId);
        ShowFacility showFacility = showFacilityRepository.findById(facilityId).orElse(null);
        ShowFacilityDTO showFacilityDTO = ShowFacilityDTO.toDTO(showFacility);
        return showFacilityDTO;
    }


}
