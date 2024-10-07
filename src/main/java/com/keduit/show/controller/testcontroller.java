package com.keduit.show.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class testcontroller {

    @GetMapping("/")
    public String test() {
        return "test";
    }

    @PostMapping("/juso")
    public @ResponseBody String dfd(String roadFullAddr){
        System.out.println("테스트 : "+ roadFullAddr);
        return "ok";
    }

}
