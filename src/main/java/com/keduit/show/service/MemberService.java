package com.keduit.show.service;

import com.keduit.show.entity.Member;
import com.keduit.show.entity.MemberImg;
import com.keduit.show.repository.MemberImgRepository;
import com.keduit.show.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final MemberImgRepository memberImgRepository;


    public Member saveMember(Member member) {
        validateMember(member);

        MemberImg memberImg = MemberImg.builder()
                .url("/images/defaultProfile.png")
                .member(member)
                .build();

        memberImgRepository.save(memberImg);

        return memberRepository.save(member);
    }



    private void validateMember(Member member) {
        Member findMember = memberRepository.findById(member.getId());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원 입니다.");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findById(userId);

        if (member == null) {
            throw new UsernameNotFoundException(userId);
        }
        return User.builder()
                .username(member.getId())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }


}
