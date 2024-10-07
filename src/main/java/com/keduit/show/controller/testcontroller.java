package com.keduit.show.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class testcontroller {

    @GetMapping("/")
    public String test() {
        return "layout1";
    }

}
