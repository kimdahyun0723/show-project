package com.keduit.show.repository;

import com.keduit.show.entity.ShowFacility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowFacilityRepository extends JpaRepository<ShowFacility, String> {

    //공연시설 존재 여부
    boolean existsByMt10id(String mt10id);
}
