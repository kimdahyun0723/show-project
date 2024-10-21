package com.keduit.show.controller;

import com.keduit.show.entity.Favorite;
import com.keduit.show.entity.Member;
import com.keduit.show.entity.Showing;
import com.keduit.show.service.CalendarService;
import com.keduit.show.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;
    private final MemberService memberService;

    //즐겨찾기 달력 표시
    @RequestMapping("/favorite/calendar")
    public String favoriteCalendar(){
        return "calendar/favoriteCalendar";
    }

    //로그인된 회원의 즐겨찾기 달력 ajax 요청
    @GetMapping("/favorite/event")
    public @ResponseBody List<Map<String, Object>> getEvent(Principal principal){
        Member member = memberService.findMember(principal.getName());
        List<Map<String, Object>> favoriteEvents = calendarService.getFavoriteList(member.getNum());

        favoriteEvents.forEach(event -> {
            System.out.println("Event: " + event);
        });

        return favoriteEvents;
    }

    //예매내역 달력 표시
    @RequestMapping("/order/calendar")
    public String orderCalendar(){
        return "calendar/orderCalendar";
    }

    @GetMapping("/order/event")
    public @ResponseBody List<Map<String, Object>> getOrderEvent(Principal principal){
        Member member = memberService.findMember(principal.getName());
        List<Map<String, Object>> orderEvents = calendarService.getOrderList(member.getNum());
        return orderEvents;
    }



}
