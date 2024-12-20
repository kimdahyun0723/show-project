package com.keduit.show.service;

import com.keduit.show.dto.FavoriteSearchDTO;
import com.keduit.show.entity.Favorite;
import com.keduit.show.repository.FavoriteRepository;
import com.keduit.show.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ShowRepository showRepository;

    //필터 페이지에 따른 즐겨찾기 전체 조회
    public Page<Favorite> getFavoriteFilterPage(FavoriteSearchDTO favoriteSearchDTO, Pageable pageable, Long memberNum) {
        return favoriteRepository.getFavoriteFilterPage(favoriteSearchDTO, pageable, memberNum);
    }

    //즐겨찾기 하나 조회
    public Favorite findOne(Long memberNum, String mt20id){
        return favoriteRepository.findByIds(memberNum, mt20id).orElse(null);
    }

    //즐겨찾기 추가
    public void save(Favorite favorite){
        favoriteRepository.save(favorite);
        showRepository.updateCount(favorite.getShowing(), true);
    }

    //즐겨찾기 삭제
    public void remove(Long favoriteNum){
        Favorite favorite = favoriteRepository.findById(favoriteNum).orElse(null);
        if(favorite != null){
            showRepository.updateCount(favorite.getShowing(), false);
        }
        favoriteRepository.remove(favoriteNum);
    }
}
