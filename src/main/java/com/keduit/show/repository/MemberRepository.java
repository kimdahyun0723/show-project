package com.keduit.show.repository;

import com.keduit.show.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findById(String id);

    Optional<Member> findByEmail(String email);
}
