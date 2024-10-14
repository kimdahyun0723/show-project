package com.keduit.show.service;

import com.keduit.show.constant.Role;
import com.keduit.show.dto.KakaoDTO;
import com.keduit.show.dto.MemberDTO;
import com.keduit.show.dto.MemberUpdateDTO;
import com.keduit.show.dto.NaverDTO;
import com.keduit.show.entity.Member;
import com.keduit.show.entity.MemberImg;
import com.keduit.show.repository.MemberImgRepository;
import com.keduit.show.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final MemberImgRepository memberImgRepository;

    private final PasswordEncoder passwordEncoder;


    public Member saveMember(Member member) {
        validateMember(member);

        MemberImg memberImg = MemberImg.builder()
                .url("/images/defaultProfile.png")
                .member(member)
                .build();

        memberImgRepository.save(memberImg);

        return memberRepository.save(member);
    }

    public void kakaoLogin(KakaoDTO kakaoDTO) {
        if (kakaoDTO == null) {
            throw new IllegalArgumentException("KakaoDTO cannot be null");
        }
        String userId = kakaoDTO.getNickname();
        Member member = memberRepository.findById(userId);


        if(member == null) {
            member = new Member();
            member.setId(kakaoDTO.getNickname());
            member.setEmail(kakaoDTO.getEmail());
            member.setRole(Role.KAKAO);
            memberRepository.save(member);
            MemberImg memberImg = MemberImg.builder()
                    .url("/images/defaultProfile.png")
                    .member(member)
                    .build();

            memberImgRepository.save(memberImg);
        }

    }  public void naverLogin(NaverDTO naverDTO) {
        if (naverDTO == null) {
            throw new IllegalArgumentException("naverDTO cannot be null");
        }
        String userId = naverDTO.getName();
        Member member = memberRepository.findById(userId);

        if(member == null) {
            member = new Member();
            member.setId(naverDTO.getId());
            member.setEmail(naverDTO.getEmail());
            member.setName(naverDTO.getName());
            member.setRole(Role.NAVER);
            memberRepository.save(member);
        }

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


    public Member findMember(String id) {
        Member member = memberRepository.findById(id);

        return member;
    }

    public void deleteMember(String name) {
        Member member = findMember(name);
        memberRepository.delete(member);
    }

    public void updateMember(String id, MemberUpdateDTO memberUpdateDTO) {
        Member member = findMember(id);
        member.updateMember(memberUpdateDTO, passwordEncoder);

    }
}
