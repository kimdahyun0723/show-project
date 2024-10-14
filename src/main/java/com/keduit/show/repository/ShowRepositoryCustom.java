package com.keduit.show.repository;

import com.keduit.show.dto.ShowSearchDTO;
import com.keduit.show.entity.Showing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShowRepositoryCustom {

    Page<Showing> getShowFilterPage(ShowSearchDTO showSearchDTO, Pageable pageable);
}
