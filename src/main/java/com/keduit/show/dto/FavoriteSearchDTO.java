package com.keduit.show.dto;

import com.keduit.show.constant.Sort;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FavoriteSearchDTO {

    //날짜순 정렬
    private Sort sort;

    //검색(이름, 시설)
    private String searchBy;
    private String searchQuery = "";
}
