package com.keduit.show.controller;

import com.keduit.show.dto.ReviewRequestDTO;
import com.keduit.show.dto.ReviewResponseDTO;
import com.keduit.show.entity.Review;
import com.keduit.show.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ReviewService reviewService;

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
}
