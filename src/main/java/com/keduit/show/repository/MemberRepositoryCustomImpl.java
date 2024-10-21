package com.keduit.show.repository;

import com.keduit.show.dto.MemberSearchDTO;
import com.keduit.show.entity.Member;
import com.keduit.show.entity.QMember;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em) { this.queryFactory = new JPAQueryFactory(em);   }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if (StringUtils.equals("id", searchBy)) {
            return QMember.member.id.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("name", searchBy)) {
            return QMember.member.name.like("%" + searchQuery + "%");
        }
        return null;
    }

    @Override
    public Page<Member> getMemberPage(MemberSearchDTO memberSearchDTO, Pageable pageable) {

        List<Member> result = queryFactory.selectFrom(QMember.member)
                .where(searchByLike(memberSearchDTO.getSearchBy(), memberSearchDTO.getSearchQuery()))
                .orderBy(QMember.member.num.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(Wildcard.count)
                .from(QMember.member)
                .where(searchByLike(memberSearchDTO.getSearchBy(), memberSearchDTO.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(result, pageable, total);
    }
}
