package com.keduit.show.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class MemberUpdateDTO {

    @NotBlank(message = "아이디는 필수 입력입니다.")
    private String id;

    @NotBlank(message = "이름은 필수 입력 입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력입니다.")
    private String email;

    @NotBlank(message = "전화번호는 필수 입력입니다.")
    private String phone;

    private String password;

}
