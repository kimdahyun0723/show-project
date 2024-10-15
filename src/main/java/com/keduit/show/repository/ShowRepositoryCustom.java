package com.keduit.show.repository;

import com.keduit.show.dto.ShowSearchDTO;
import com.keduit.show.entity.Showing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShowRepositoryCustom {

    Page<Showing> getShowFilterPage(ShowSearchDTO showSearchDTO, Pageable pageable);

    void updateCount(Showing showing, boolean increament);

    //오늘을 기준으로 전후 일주일 공연 리스트 반환
    List<Showing> getShowWeeksList();
}
