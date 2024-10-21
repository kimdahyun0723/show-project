package com.keduit.show.controller;

import com.keduit.show.constant.Sort;
import com.keduit.show.dto.ReviewRequestDTO;
import com.keduit.show.dto.ReviewResponseDTO;
import com.keduit.show.dto.ReviewSearchDTO;
import com.keduit.show.entity.Member;
import com.keduit.show.entity.Review;
import com.keduit.show.entity.Showing;
import com.keduit.show.repository.ReviewRepository;
import com.keduit.show.service.MemberService;
import com.keduit.show.service.ReviewService;
import com.keduit.show.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberService memberService;
    private final ShowService showService;
    private final ReviewRepository reviewRepository;

    //리뷰추가
    @PostMapping("/show/{mt20id}/review")
    @ResponseBody
    public ResponseEntity<ReviewResponseDTO> save(@PathVariable String mt20id
            , @RequestBody ReviewRequestDTO reviewRequestDTO, Principal principal){
        Review review = reviewService.save(principal.getName(), mt20id, reviewRequestDTO);
        ReviewResponseDTO reviewResponseDTO = ReviewResponseDTO.toDTO(review);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDTO);
    }

    //리뷰 수정
    @PostMapping("/show/{mt20id}/review/{num}")
    @ResponseBody
    public ResponseEntity<ReviewResponseDTO> update(@PathVariable String mt20id
            , @PathVariable Long num, @RequestBody ReviewRequestDTO reviewRequestDTO) {
        Review review = reviewService.update(num, reviewRequestDTO);
        ReviewResponseDTO reviewResponseDTO = ReviewResponseDTO.toDTO(review);
        return ResponseEntity.ok(reviewResponseDTO);
    }

    //리뷰 삭제
    @DeleteMapping("/show/{mt20id}/review/{num}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable String mt20id, @PathVariable Long num) {
        reviewService.delete(num);
        return ResponseEntity.ok().build();
    }

    //로그인된 유저의 마이페이지 공연후기 리스트 조회
//    @GetMapping(value = "/reviews")
//    public String review(Model model, Principal principal) {
//        Member member = memberService.findMember(principal.getName());
//        List<ReviewResponseDTO> reviews = reviewService.findReviewByMember(member.getNum());
//        model.addAttribute("reviews", reviews);
//
//        //공연명을 저장할 Map 생성
//        Map<Long, String> prfnmMap = new HashMap<>();
//        //각 리뷰에 대한 공연명 조회
//        for (ReviewResponseDTO review : reviews) {
//            String prfnm = reviewService.getPrfnmByReviewNum(review.getNum());
//            prfnmMap.put(review.getNum(), prfnm);
//        }
//        model.addAttribute("prfnmMap", prfnmMap);
//
//        return "show/review";
//    }

    //로그인된 유저의 마이페이지 공연 후기 리스트 조회 (필터, 검색, 정렬)
    @GetMapping({"/reviews", "/reviews/{page}"})
    public String review(ReviewSearchDTO searchDTO, @PathVariable("page")Optional<Integer> page,
                         Model model, Principal principal) {
        //기본값 정렬 설정
        if(searchDTO.getSort() == null){
            searchDTO.setSort(Sort.DEFAULT);
        }
        //한페이지에 리뷰 10개
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

        Member member = memberService.findMember(principal.getName());
        Page<Review> reviews = reviewService.getReviewFilterPage(searchDTO, pageable, member.getNum());
        model.addAttribute("reviews", reviews);
        model.addAttribute("searchDTO", searchDTO);
        model.addAttribute("maxPage", 5);

        //공연명을 저장할 Map 생성
        Map<Long, String> prfnmMap = new HashMap<>();
        //각 리뷰에 대한 공연명 조회
        for (Review review : reviews) {
            String prfnm = reviewService.getPrfnmByReviewNum(review.getNum());
            prfnmMap.put(review.getNum(), prfnm);
        }
        model.addAttribute("prfnmMap", prfnmMap);
        return "show/review";
    }

}
