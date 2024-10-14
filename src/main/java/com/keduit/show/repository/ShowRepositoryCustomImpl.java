package com.keduit.show.repository;

import com.keduit.show.constant.Genre;
import com.keduit.show.constant.Location;
import com.keduit.show.constant.Sort;
import com.keduit.show.constant.State;
import com.keduit.show.dto.ShowSearchDTO;
import com.keduit.show.entity.QShowing;
import com.keduit.show.entity.Showing;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class ShowRepositoryCustomImpl implements ShowRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ShowRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    //장르필터
    private BooleanExpression searchGenreEq(Genre searchGenre){
        return searchGenre == null ? null : QShowing.showing.genrenm.eq(searchGenre);
    }

    //지역필터
    private BooleanExpression searchLocationEq(Location searchLocation){
        return searchLocation == null ? null : QShowing.showing.area.eq(searchLocation);
    }

    //상태필터
    private BooleanExpression searchStateEq(State searchState){
        return searchState == null ? null : QShowing.showing.prfstate.eq(searchState);
    }

    //검색필터 : 공연명, 시설명
    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("prfnm", searchBy)){
            return QShowing.showing.prfnm.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("fcltynm", searchBy)){
            return QShowing.showing.fcltynm.like("%"+searchQuery+"%");
        }
        return null;
    }

    // 정렬 : 최신순, 오래된순
    private OrderSpecifier<?> createOrderSpecifier(Sort sort) {
        if(sort == null || sort == Sort.DEFAULT){ //null 처리
            return QShowing.showing.mt20id.asc();
        } else if (sort == Sort.DATE_ASC) {
            return QShowing.showing.prfpdfrom.asc(); // 오래된순
        } else if (sort == Sort.DATE_DESC) {
            return QShowing.showing.prfpdfrom.desc(); // 최신순
        }
        return null; // 기본 정렬 없음
    }

    //필터, 페이징 적용
    @Override
    public Page<Showing> getShowFilterPage(ShowSearchDTO showSearchDTO, Pageable pageable) {
        System.out.println("getShowListPage/showSearchDTO----------------------" + showSearchDTO);
        System.out.println("getShowListPage/pageable------------------------" + pageable);

        List<Showing> result = queryFactory.selectFrom(QShowing.showing)
                .where(searchGenreEq(showSearchDTO.getSearchGenre())
                        , searchLocationEq(showSearchDTO.getSearchLocation())
                        , searchStateEq(showSearchDTO.getSearchState())
                        , searchByLike(showSearchDTO.getSearchBy(), showSearchDTO.getSearchQuery()))
                .orderBy(createOrderSpecifier(showSearchDTO.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(Wildcard.count)
                .from(QShowing.showing)
                .where(searchGenreEq(showSearchDTO.getSearchGenre())
                        , searchLocationEq(showSearchDTO.getSearchLocation())
                        , searchStateEq(showSearchDTO.getSearchState())
                        , searchByLike(showSearchDTO.getSearchBy(), showSearchDTO.getSearchQuery()))
                .orderBy(createOrderSpecifier(showSearchDTO.getSort()))
                .fetchOne();
        return new PageImpl<>(result, pageable, total);
    }
}
