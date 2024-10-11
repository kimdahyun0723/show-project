package com.keduit.show.repository;

import com.keduit.show.dto.BoardSearchDTO;
import com.keduit.show.entity.Board;
import com.keduit.show.entity.QBoard;
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

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {


    private JPAQueryFactory queryFactory;

    public BoardRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
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
        return QBoard.board.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if (StringUtils.equals("title", searchBy)) {
            return QBoard.board.title.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createBy", searchBy)) {
            return QBoard.board.createBy.like("%" + searchQuery + "%");
        }
        return null;
    }

    @Override
    public Page<Board> getBoardsPage(BoardSearchDTO boardSearchDTO, Pageable pageable) {
        System.out.println("[getBoardsPage]boardSearchDTO -------------- : " + boardSearchDTO);
        System.out.println("[getBoardsPage]pageable -------------------- : " + pageable);

        List<Board> result = queryFactory.selectFrom(QBoard.board)
                .where(regDtsAfter(boardSearchDTO.getSearchDateType()),
                        searchByLike(boardSearchDTO.getSearchBy(), boardSearchDTO.getSearchQuery()))
                .orderBy(QBoard.board.num.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(Wildcard.count)
                .from(QBoard.board)
                .where(regDtsAfter(boardSearchDTO.getSearchDateType()),
                        searchByLike(boardSearchDTO.getSearchBy(), boardSearchDTO.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(result, pageable, total);
    }
}
