package com.keduit.show.controller;

import com.keduit.show.constant.Sort;
import com.keduit.show.dto.*;
import com.keduit.show.entity.Favorite;
import com.keduit.show.entity.Member;
import com.keduit.show.entity.Review;
import com.keduit.show.entity.Showing;
import com.keduit.show.repository.FavoriteRepository;
import com.keduit.show.repository.MemberRepository;
import com.keduit.show.service.MemberService;
import com.keduit.show.service.ReviewService;
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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ShowController {

    //공연 페이지 요청 컨트롤러

    @Autowired
    private ShowService showService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ReviewService reviewService;

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
        return "show/showList"; //공연목록페이지
    }


    //상세조회
    @GetMapping("/show/{mt20id}")
    public String showDetail(@PathVariable("mt20id") String mt20id, Model model, Principal principal) {
        //공연정보 전달
        ShowingDTO showingDTO = showService.getShowById(mt20id);
        ShowFacilityDTO showFacilityDTO = showService.getShowFacilityById(mt20id); //시설상세
        model.addAttribute("show", showingDTO);
        model.addAttribute("showFacility", showFacilityDTO);

        //로그인 체크 여부 전달
        model.addAttribute("isLogin", principal != null);


        //공연후기 리스트 전달
        List<ReviewResponseDTO> reviewResponseDTOS = reviewService.read(mt20id);
        model.addAttribute("reviews", reviewResponseDTOS);

        //리뷰 작성 js 에서 쓸거
        model.addAttribute("mt20id", mt20id); //공연아이디
        String memberId = principal != null ? principal.getName() : "no";
        model.addAttribute("memberId", memberId); //로그인회원아이디

        //즐겨찾기 버튼 활성화
        if (principal != null) { //로그인 여부
            String id = principal.getName();
            Member member = memberService.findMember(principal.getName());
            model.addAttribute("member", member);
            //즐겨찾기 여부 전달
            List<Favorite> favorites = member.getFavorites();
            for (Favorite favorite : favorites) {
                if (favorite.getShowing().getMt20id().equals(mt20id)) {
                    model.addAttribute("favoriteShow", "btn-primary");
                    return "show/showDetail";
                }
            }
        }
        model.addAttribute("favoriteShow", "btn-outline-primary");

        return "show/showDetail";
    }

}
