package com.keduit.show.controller;

import com.keduit.show.dto.BoardSearchDTO;
import com.keduit.show.dto.ImageResponseDTO;
import com.keduit.show.dto.MemberDTO;
import com.keduit.show.dto.MemberUpdateDTO;
import com.keduit.show.entity.Board;
import com.keduit.show.entity.Member;
import com.keduit.show.entity.Review;
import com.keduit.show.repository.MemberImgRepository;
import com.keduit.show.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final MemberImgService memberImgService;

    private final BoardService boardService;

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
        model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());
        return "member/loginForm";
    }

    @GetMapping("/info")
    public String memberInfo(Model model, Principal principal) {
        Member member = memberService.findMember(principal.getName());
        ImageResponseDTO image = memberImgService.findImage(principal.getName());

        model.addAttribute("member", member);
        model.addAttribute("image", image);


        return "/member/info";
    }

    @GetMapping("/updateInfo")
    public String updateInfo(Model model, Principal principal) {
        Member member = memberService.findMember(principal.getName());
        model.addAttribute("member", member);

        ImageResponseDTO image = memberImgService.findImage(principal.getName());
        model.addAttribute("image", image);

        return "member/updateInfo";
    }

    @GetMapping("/delete")
    public String delete(Principal principal) {
        memberService.deleteMember(principal.getName());

        return "redirect:/members/logout";
    }


    @PostMapping("/update")
    public String update(@Valid MemberUpdateDTO memberUpdateDTO, BindingResult bindingResult, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "redirect:/members/updateInfo";
        }
        System.out.println("=======================" + memberUpdateDTO.getPassword() + " ========================================password");

        memberService.updateMember(principal.getName(), memberUpdateDTO);
        return "redirect:/members/info";
    }

    @GetMapping({"myBoards", "myBoards/{page}"})
    public String myBoards(Model model, Principal principal, @PathVariable("page") Optional<Integer> page, BoardSearchDTO searchDTO) {
        Member member = memberService.findMember(principal.getName());

        //한페이지에 리뷰 10개
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

        Page<Board> boardPage = boardService.getBoardsPageWithMember(searchDTO, pageable, member.getNum());
        model.addAttribute("boards", boardPage);


        ImageResponseDTO image = memberImgService.findImage(principal.getName());
        model.addAttribute("image", image);
        model.addAttribute("member", member);
        model.addAttribute("maxPage", 5);
        model.addAttribute("searchDTO", searchDTO);
        return "member/myBoards";
    }

}
