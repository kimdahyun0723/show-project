package com.keduit.show.repository;

import com.keduit.show.constant.Sort;
import com.keduit.show.dto.FavoriteSearchDTO;
import com.keduit.show.entity.Favorite;
import com.keduit.show.entity.QFavorite;
import com.keduit.show.entity.QReview;
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

public class FavoriteRepositoryCustomImpl implements FavoriteRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public FavoriteRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //검색 필터
    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("prfnm", searchBy)){
            return QFavorite.favorite.showing.prfnm.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("fcltynm", searchBy)){
            return QFavorite.favorite.showing.fcltynm.like("%"+searchQuery+"%");
        }
        return null;
    }

    //정렬
    private OrderSpecifier<?> createOrderSpecifier(Sort sort){
        if(sort == null || sort == Sort.DEFAULT){
            return QFavorite.favorite.num.asc();
        }else if(sort == Sort.DATE_ASC){
            return QFavorite.favorite.showing.prfpdfrom.asc();
        }else if(sort == Sort.DATE_DESC){
            return QFavorite.favorite.showing.prfpdfrom.desc();
        }
        return null;
    }

    //특정회원의 즐겨찾기 필터 페이징 적용 리스트
    @Override
    public Page<Favorite> getFavoriteFilterPage(FavoriteSearchDTO favoriteSearchDTO, Pageable pageable, Long memberNum){
        List<Favorite> result = queryFactory.selectFrom(QFavorite.favorite)
                .where(searchByLike(favoriteSearchDTO.getSearchBy(), favoriteSearchDTO.getSearchQuery()))
                .where(QFavorite.favorite.member.num.eq(memberNum))
                .orderBy(createOrderSpecifier(favoriteSearchDTO.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long total = queryFactory.select(Wildcard.count)
                .from(QFavorite.favorite)
                .where(searchByLike(favoriteSearchDTO.getSearchBy(), favoriteSearchDTO.getSearchQuery()))
                .where(QFavorite.favorite.member.num.eq(memberNum))
                .orderBy(createOrderSpecifier(favoriteSearchDTO.getSort()))
                .fetchOne();
        return new PageImpl<>(result, pageable, total);
    }
}
