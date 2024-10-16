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
import java.time.LocalDate;
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

    //공연의 즐겨찾기 갯수 더하기 빼기
    public void updateCount(Showing showing2, boolean b){
        if(b){
            queryFactory.update(QShowing.showing)
                    .set(QShowing.showing.likeCount, QShowing.showing.likeCount.add(1))
                    .where(QShowing.showing.mt20id.eq(showing2.getMt20id()))
                    .execute();
        }else{
            queryFactory.update(QShowing.showing)
                    .set(QShowing.showing.likeCount, QShowing.showing.likeCount.subtract(1))
                    .where(QShowing.showing.mt20id.eq(showing2.getMt20id()))
                    .execute();
        }
    }

    //오늘을 기준으로 전후 일주일 공연 리스트 반환
    public List<Showing> getShowWeeksList(){
        LocalDate now = LocalDate.now();
        LocalDate start = now.minusWeeks(1);
        LocalDate end = now.plusWeeks(1);
        return queryFactory.select(QShowing.showing)
                .from(QShowing.showing)
                .where(QShowing.showing.prfpdto.between(start, end))
                .orderBy(QShowing.showing.prfpdto.desc()) //내림차순
                .fetch();
    }

    //장르필터 공연 리스트 반환
    public List<Showing> getShowFilterGenre(ShowSearchDTO showSearchDTO){
        return  queryFactory.selectFrom(QShowing.showing)
                .where(searchGenreEq(showSearchDTO.getSearchGenre()))
                .where(QShowing.showing.prfstate.eq(State.ING)) //상영중인 공연
                .fetch();
    }
}
