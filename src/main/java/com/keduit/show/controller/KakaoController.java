package com.keduit.show.controller;

import com.keduit.show.dto.KakaoDTO;
import com.keduit.show.entity.Member;
import com.keduit.show.entity.MsgEntity;
import com.keduit.show.service.KakaoService;
import com.keduit.show.service.MemberService;
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
@RequestMapping("kakao")
public class KakaoController {

    private final KakaoService kakaoService;

    private final MemberService memberService;

    @GetMapping("/callback")
    public void callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));

        if (kakaoInfo != null) {
            // 세션에 사용자 정보 저장
            HttpSession session = request.getSession();
            session.setAttribute("user", kakaoInfo);
            session.setAttribute("isLoggedIn", true);

            // 사용자 인증 정보 생성
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    kakaoInfo.getNickname(), null, new ArrayList<>()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 메인 페이지로 리다이렉트
            response.sendRedirect("http://localhost:8282");
        } else {
            response.sendRedirect("http://localhost:8282/login?error=true");
        }


    }


}
