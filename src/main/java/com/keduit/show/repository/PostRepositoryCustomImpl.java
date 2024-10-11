package com.keduit.show.repository;

import com.keduit.show.dto.PostSearchDTO;
import com.keduit.show.entity.Post;
import com.keduit.show.entity.QPost;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public PostRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Post> getBoardsPage(PostSearchDTO postSearchDTO, Pageable pageable) {
        List<Post> result = queryFactory.selectFrom(QPost.post)
                .where(regDtsAfter(postSearchDTO.getSearchDateType()),
                        searchByLike(postSearchDTO.getSearchBy(), postSearchDTO.getSearchQuery()))
            .orderBy(QPost.post.num.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(Wildcard.count)
                .from(QPost.post)
                .where(regDtsAfter(postSearchDTO.getSearchDateType()),
                        searchByLike(postSearchDTO.getSearchBy(), postSearchDTO.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(result, pageable, total);
    }

    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();
        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }
        return QPost.post.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if (StringUtils.equals("title", searchBy)) {
            return QPost.post.title.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createBy", searchBy)) {
            return QPost.post.createBy.like("%" + searchQuery + "%");
        }
        return null;
    }

}
