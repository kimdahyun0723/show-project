package com.keduit.show.repository;

import com.keduit.show.dto.MemberSearchDTO;
import com.keduit.show.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {

    Page<Member> getMemberPage(MemberSearchDTO memberSearchDTO, Pageable pageable);
}
