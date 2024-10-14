package com.keduit.show.dto;

import com.keduit.show.constant.Genre;
import com.keduit.show.constant.Location;
import com.keduit.show.constant.Sort;
import com.keduit.show.constant.State;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShowSearchDTO {

    //공연목록필터
    private Genre searchGenre; //장르
    private Location searchLocation; //지역
    private State searchState; //공연상태

    //날짜순 정렬
    private Sort sort;

    //검색(이름, 시설)
    private String searchBy;
    private String searchQuery = "";

}
