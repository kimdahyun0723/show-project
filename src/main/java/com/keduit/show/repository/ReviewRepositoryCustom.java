package com.keduit.show.repository;

import com.keduit.show.dto.ReviewSearchDTO;
import com.keduit.show.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {

    Page<Review> getReviewFilterPage(ReviewSearchDTO reviewSearchDTO, Pageable pageable, Long memberNum);
}
