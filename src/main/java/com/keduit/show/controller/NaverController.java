package com.keduit.show.controller;

import com.keduit.show.dto.NaverDTO;
import com.keduit.show.entity.MsgEntity;
import com.keduit.show.service.NaverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("naver")
public class NaverController {

    private final NaverService naverService;

    @GetMapping("/callback")
    public void callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        NaverDTO naverInfo = naverService.getNaverInfo(request.getParameter("code"));

        if (naverInfo != null) {
            // 세션에 사용자 정보 저장
            HttpSession session = request.getSession();
            session.setAttribute("user", naverInfo);
            session.setAttribute("isLoggedIn", true);

            // 사용자 인증 정보 생성
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    naverInfo.getId(), null, new ArrayList<>()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 메인 페이지로 리다이렉트
            response.sendRedirect("http://localhost:8282");
        } else {
            response.sendRedirect("http://localhost:8282/login?error=true");
        }
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("isLoggedIn");
        response.sendRedirect("http://localhost:8282");
    }

}
