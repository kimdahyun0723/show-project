package com.keduit.show.repository;

import com.keduit.show.entity.Member;
import com.keduit.show.entity.MemberImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberImgRepository extends JpaRepository<MemberImg, Long> {

    MemberImg findByMember(Member member);

}
