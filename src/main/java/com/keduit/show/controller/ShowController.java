package com.keduit.show.controller;

import com.keduit.show.constant.Sort;
import com.keduit.show.dto.*;
import com.keduit.show.entity.Showing;
import com.keduit.show.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ShowController {

    //공연 페이지 요청 컨트롤러

    @Autowired
    private ShowService showService;

    //전체 목록 확인
    @GetMapping({"/shows", "/shows/{page}"})
    public String showList(ShowSearchDTO showSearchDTO,
                           @PathVariable("page")Optional<Integer> page,
                           Model model) {
        if (showSearchDTO.getSort() == null) {
            showSearchDTO.setSort(Sort.DEFAULT); // 기본값 설정
        }
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 8);
        Page<Showing> shows = showService.getShowFilterPage(showSearchDTO, pageable);
        model.addAttribute("showLists", shows);
        model.addAttribute("showSearchDTO", showSearchDTO);
        model.addAttribute("maxPage", 5);
        return "show/showList";
    }


    //상세조회
    @GetMapping("/show/{mt20id}")
    public String showDetail(@PathVariable("mt20id") String mt20id, Model model) {
        ShowingDTO showingDTO = showService.getShowById(mt20id);
        ShowFacilityDTO showFacilityDTO = showService.getShowFacilityById(mt20id); //시설상세
        model.addAttribute("show", showingDTO);
        model.addAttribute("showFacility", showFacilityDTO);
        return "show/showDetail";
    }

}
