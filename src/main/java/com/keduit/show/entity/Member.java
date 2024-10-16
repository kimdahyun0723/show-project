package com.keduit.show.entity;

import com.keduit.show.constant.Role;
import com.keduit.show.dto.MemberDTO;
import com.keduit.show.dto.MemberUpdateDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Member extends BaseEntity {

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


    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private MemberImg memberImg;

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies;

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards;

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;



    public static Member createMember(MemberDTO memberDTO, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setName(memberDTO.getName());
        member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        member.setEmail(memberDTO.getEmail());
        member.setPhone(memberDTO.getPhone());
        member.setRole(Role.USER);
        return member;
    }

    public void updateMember(MemberUpdateDTO memberDTO, PasswordEncoder passwordEncoder) {
        this.name = memberDTO.getName();
        if (memberDTO.getPassword() != null && !memberDTO.getPassword().isEmpty()) {
            this.password = passwordEncoder.encode(memberDTO.getPassword());
        }
        this.email = memberDTO.getEmail();
        this.phone = memberDTO.getPhone();
    }

    //즐겨찾기 추가
    public void addFavorite(Favorite favorite) {
        for(Favorite f : favorites) {
            if(f.getShowing().equals(favorite.getShowing())) return;
        }
        favorites.add(favorite);
        favorite.setMember(this);
    }
    //즐겨찾기 삭제
    public void removeFavorite(Favorite favorite) {
        favorites.remove(favorite);
        favorite.setMember(null);
    }





}
