package com.keduit.show.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class testcontroller {

    @GetMapping("/")
    public String test() {
        return "test";
    }

}
