package com.keduit.show.controller;

import com.keduit.show.service.ShowApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class ShowApiController {

    //api 요청 컨트롤러

    @Autowired
    private ShowApiService showApiService;

    //api 수동 실행
    @GetMapping("/item/api")
    public String saveShow() throws Exception {
        showApiService.saveShow();
        showApiService.saveShowFacility();
        showApiService.deleteShow();
        return "main";
    }

}
