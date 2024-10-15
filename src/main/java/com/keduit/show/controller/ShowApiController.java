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
    @GetMapping("/show/api")
    public String saveShow() throws Exception {
        showApiService.saveShow(); //공연목록
        showApiService.saveShowFacility(); //시설목록
        showApiService.deleteShow(); //공연완료후 2주일이 지나면 삭제
        return "main";
    }

}
