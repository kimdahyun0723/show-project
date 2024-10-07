package com.keduit.show.entity;

import com.keduit.show.constant.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    


}
