package com.keduit.show.entity;

import com.keduit.show.constant.Role;
import com.keduit.show.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_num")
    private Long num;

    @Column(unique = true)
    private String id;

    private String name;

    private String password;

    private String email;

    private String phone;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String imgname;

    private String imgUrl;

    public static Member createMember(MemberDTO memberDTO, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setName(memberDTO.getName());
        member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        member.setEmail(memberDTO.getEmail());
        member.setPhone(memberDTO.getPhone());
        member.setAddress(memberDTO.getAddress());
        member.setRole(Role.USER);
        member.setImgname(memberDTO.getImgname());
        member.setImgUrl(memberDTO.getImgUrl());
        return member;
    }




}
