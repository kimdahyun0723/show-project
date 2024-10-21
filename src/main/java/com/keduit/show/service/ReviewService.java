package com.keduit.show.service;

import com.keduit.show.dto.ReviewRequestDTO;
import com.keduit.show.dto.ReviewResponseDTO;
import com.keduit.show.dto.ReviewSearchDTO;
import com.keduit.show.entity.Member;
import com.keduit.show.entity.Review;
import com.keduit.show.entity.Showing;
import com.keduit.show.repository.MemberRepository;
import com.keduit.show.repository.ReviewRepository;
import com.keduit.show.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ShowRepository showRepository;

    //특정 회원의 리뷰 리스트 조회
    public List<ReviewResponseDTO> findReviewByMember(Long memberNum){
        List<Review> reviews = reviewRepository.findByMember(memberNum);
        List<ReviewResponseDTO> reviewResponseDTOS = reviews.stream()
                .map(review -> ReviewResponseDTO.toDTO(review))
                .collect(Collectors.toList());
        return reviewResponseDTOS;
    }

    //리뷰 번호를 기준으로 공연 이름 조회
    public String getPrfnmByReviewNum(Long reviewNum) {
        return reviewRepository.findPrfnmByReviewNum(reviewNum);
    }

    //필터 페이지에 따른 리뷰 전체 조회
    public Page<Review> getReviewFilterPage(ReviewSearchDTO reviewSearchDTO, Pageable pageable, Long memberNum) {
        return reviewRepository.getReviewFilterPage(reviewSearchDTO, pageable, memberNum);
    }


    //리뷰추가
    public Review save(String memberId, String mt20id, ReviewRequestDTO reviewRequestDTO) {
        Member member = memberRepository.findById(memberId);
        Showing showing = showRepository.findById(mt20id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공연이 존재하지 않습니다"));
        reviewRequestDTO.setMember(member);
        reviewRequestDTO.setShowing(showing);
        Review review = reviewRequestDTO.toEntity();
        reviewRepository.save(review);
        return review;
    }

    //리뷰 전체 조회
    public List<ReviewResponseDTO> read(String mt20id) {
        Showing showing = showRepository.findById(mt20id).orElseThrow(
                () -> new IllegalArgumentException("해당 공연을 찾을수 없습니다"));
        List<Review> reviews = reviewRepository.findByShowing(showing);
        List<ReviewResponseDTO> reviewResponseDTOS = reviews.stream()
                .map(review -> ReviewResponseDTO.toDTO(review))
                .collect(Collectors.toList());
        return reviewResponseDTOS;
    }

    //리뷰 수정
    public Review update(Long num, ReviewRequestDTO reviewRequestDTO) {
        System.out.println("service num =========================" + num);
        Review review = reviewRepository.findById(num)
                        .orElseThrow(() -> new IllegalArgumentException("후기를 찾을수 없습니다"));
        System.out.println(review.getRating() + "service -------------------------");
        review.update(reviewRequestDTO.getRating(), reviewRequestDTO.getContent());
        System.out.println(review.getRating() + "service update -------------------------");
        return reviewRepository.save(review); //수정후 반환
    }

    //리뷰 삭제
    public void delete(Long num) {
        reviewRepository.deleteById(num);
    }

}
