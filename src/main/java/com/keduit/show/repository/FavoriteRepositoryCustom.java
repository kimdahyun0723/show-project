package com.keduit.show.repository;

import com.keduit.show.dto.FavoriteSearchDTO;
import com.keduit.show.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoriteRepositoryCustom {

    Page<Favorite> getFavoriteFilterPage(FavoriteSearchDTO favoriteSearchDTO, Pageable pageable, Long memberNum);
}
