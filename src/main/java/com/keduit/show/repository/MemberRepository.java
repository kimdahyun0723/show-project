package com.keduit.show.repository;

import com.keduit.show.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findById(String id);

}
