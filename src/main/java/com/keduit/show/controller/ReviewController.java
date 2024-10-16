package com.keduit.show.controller;

import com.keduit.show.dto.ReviewRequestDTO;
import com.keduit.show.entity.Review;
import com.keduit.show.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    //리뷰추가
    @PostMapping("/show/{mt20id}/review")
    public ResponseEntity<Review> save(@PathVariable String mt20id
            , @RequestBody ReviewRequestDTO reviewRequestDTO, Principal principal) {
        Review review = reviewService.save(mt20id, principal.getName(), reviewRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    //리뷰조회
    @GetMapping("/show/{mt20id}/review")
    public List<Review> read(@PathVariable String mt20id) {
        return reviewService.findAll(mt20id);
    }

    //리뷰 수정
    @PutMapping("/show/{mt20id}/review/{num}")
    public ResponseEntity<Long> update(@PathVariable String mt20id
            , @PathVariable Long num, @RequestBody ReviewRequestDTO reviewRequestDTO) {
        reviewService.update(mt20id, num, reviewRequestDTO);
        return ResponseEntity.ok(num);
    }

    //리뷰 삭제
//    @DeleteMapping
}
