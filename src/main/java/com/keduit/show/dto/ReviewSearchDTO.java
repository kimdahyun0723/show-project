package com.keduit.show.dto;

import com.keduit.show.constant.Sort;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewSearchDTO {

    //날짜순 정렬
    private Sort sort;

    //검색(공연명, 내용)
    private String searchBy;
    private String searchQuery;
}
