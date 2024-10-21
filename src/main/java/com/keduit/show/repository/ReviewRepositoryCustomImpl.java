package com.keduit.show.repository;

import com.keduit.show.constant.Sort;
import com.keduit.show.dto.ReviewSearchDTO;
import com.keduit.show.entity.QReview;
import com.keduit.show.entity.Review;
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

public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ReviewRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //검색 필터
    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("prfnm", searchBy)){
            return QReview.review.showing.prfnm.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("content", searchBy)){
            return QReview.review.content.like("%"+searchQuery+"%");
        }
        return null;
    }

    //정렬
    private OrderSpecifier<?> createOrderSpecifier(Sort sort){
        if(sort == null || sort == Sort.DEFAULT){
            return QReview.review.num.asc();
        }else if(sort == Sort.DATE_ASC){
            return QReview.review.createDate.asc();
        }else if(sort == Sort.DATE_DESC){
            return QReview.review.createDate.desc();
        }
        return null;
    }

    //특정 회원의 필터 페이지 적용 리뷰 리스트
    @Override
    public Page<Review> getReviewFilterPage(ReviewSearchDTO reviewSearchDTO, Pageable pageable, Long memberNum){
        List<Review> result = queryFactory.selectFrom(QReview.review)
                .where(searchByLike(reviewSearchDTO.getSearchBy(), reviewSearchDTO.getSearchQuery()))
                .where(QReview.review.member.num.eq(memberNum))
                .orderBy(createOrderSpecifier(reviewSearchDTO.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(Wildcard.count)
                .from(QReview.review)
                .where(searchByLike(reviewSearchDTO.getSearchBy(), reviewSearchDTO.getSearchQuery()))
                .where(QReview.review.member.num.eq(memberNum))
                .orderBy(createOrderSpecifier(reviewSearchDTO.getSort()))
                .fetchOne();
        return new PageImpl<>(result, pageable, total);
    }
}
