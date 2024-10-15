package com.keduit.show.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class OrderDTO {

    private String showNum;

    @Min(value = 1, message = "최소 주문수량은 1개 이상 입니다.")
    @Max(value = 4, message = "최대 주문 수량은 4개 입니다.")
    private int count;
}
