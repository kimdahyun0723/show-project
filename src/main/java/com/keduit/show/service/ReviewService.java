package com.keduit.show.service;

import com.keduit.show.dto.ReviewRequestDTO;
import com.keduit.show.entity.Member;
import com.keduit.show.entity.Review;
import com.keduit.show.entity.Showing;
import com.keduit.show.repository.MemberRepository;
import com.keduit.show.repository.ReviewRepository;
import com.keduit.show.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ShowRepository showRepository;

    //리뷰추가
    public Review save(String mt20id, String createBy, ReviewRequestDTO reviewRequestDTO) {
        Member member = memberRepository.findById(createBy);
        Showing showing = showRepository.findById(mt20id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공연이 없습니다"));
        reviewRequestDTO.setShowing(mt20id);
        reviewRequestDTO.setCreateBy(createBy);
        return reviewRepository.save(reviewRequestDTO.toEntity(showing, member));
    }

    //리뷰 전체 조회
    @Transactional(readOnly = true)
    public List<Review> findAll(String mt20id) {
        Showing showing = showRepository.findById(mt20id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공연이 없습니다"));
        List<Review> reviews = showing.getReviews();
        return reviews;
    }

    //리뷰 수정
    @Transactional
    public void update(String mt20id, Long num, ReviewRequestDTO reviewRequestDTO) {
        Review review = reviewRepository.findByShowingMt20idAndNum(mt20id, num)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다" + num));
        review.update(review.getRating(), review.getContent());
    }

    //리뷰 삭제
    @Transactional
    public void delete(String mt20id, Long num) {
        Review review = reviewRepository.findByShowingMt20idAndNum(mt20id, num)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다" + num));
        reviewRepository.delete(review);
    }

}
