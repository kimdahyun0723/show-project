package com.keduit.show.dto;

import com.keduit.show.constant.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberListDTO {

    Long num;

    String id;

    String name;

    String email;

    String phone;

    Role role;

}
