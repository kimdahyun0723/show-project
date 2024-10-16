package com.keduit.show.controller;

import com.keduit.show.entity.Member;
import com.keduit.show.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class Admincontroller {

    private final MemberService memberService;
    private final BoardService boardService;
    private final ReplyService replyService;
    private final ShowService showService;
    private final ShowApiService showApiService;

    @GetMapping("/adminpage")
    public String adminpage() {
        return "admin/main";
    }

    @GetMapping("/managementAPI")
    public String managementAPI() throws Exception {
        return "admin/API";
    }

    @GetMapping("/managementAPI/update")
    public String managementAPIUpdate() throws Exception {
        showApiService.saveShow(); //공연목록
        showApiService.saveShowFacility(); //시설목록
        return "admin/API";
    }

    @PostMapping("/managementAPI/delete")
    public String managementAPIDelete(@RequestParam("standard") Integer standard) throws Exception {
        showApiService.deleteShow(standard);
        return "admin/API";
    }



}
