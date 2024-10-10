package com.keduit.show.controller;

import com.keduit.show.dto.ImageResponseDTO;
import com.keduit.show.dto.MemberDTO;
import com.keduit.show.entity.Member;
import com.keduit.show.repository.MemberImgRepository;
import com.keduit.show.service.KakaoService;
import com.keduit.show.service.MemberImgService;
import com.keduit.show.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final MemberImgService memberImgService;

    private final KakaoService kakaoService;


    private final PasswordEncoder passwordEncoder;

    @GetMapping("/agree")
    public String agree() {
        return "member/agree";
    }

    @GetMapping("/new")
    public String newMember(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "member/memberForm";
    }

    @PostMapping("/new")
    public String newMember(@Valid MemberDTO memberDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "member/memberForm";
        }
        try {
            Member member = Member.createMember(memberDTO, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
        return  "redirect:/";
    }
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());

        return "member/loginForm";
    }

    @GetMapping("/login/error")
    public  String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "member/loginForm";
    }

    @GetMapping("/info")
    public String memberInfo(Model model, Principal principal) {
        System.out.println("=================================" + principal.getName());
        Member member = memberService.findMember(principal.getName());
        ImageResponseDTO image = memberImgService.findImage(principal.getName());

        model.addAttribute("member", member);
        model.addAttribute("image", image);


        return "/member/info";
    }

    @GetMapping("/updateInfo")
    public String updateInfo(Model model, Principal principal) {
        System.out.println("name===============================" + principal.getName());
        Member member = memberService.findMember(principal.getName());

        model.addAttribute("member", member);

        return "member/updateInfo";
    }

    @GetMapping("/delete")
    public String delete(Principal principal) {
        memberService.deleteMember(principal.getName());

        return "redirect:/members/logout";
    }

    @PostMapping("/update")
    public String update(@Valid MemberDTO memberDTO, BindingResult bindingResult, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "member/updateInfo";
        }

        memberService.updateMember(principal.getName(), memberDTO);



        return "redirect:/members/info";
    }

}
