package com.keduit.show.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class PostDTO {

    Long PostId;

    @NotEmpty(message = "제목은 필수 입력 입니다.")
    private String title;

    @NotEmpty(message = "제목은 필수 입력 입니다.")
    private String content;

    private String createBy;




}
