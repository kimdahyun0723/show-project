package com.keduit.show.controller;

import com.keduit.show.dto.BoardSearchDTO;
import com.keduit.show.dto.MemberListDTO;
import com.keduit.show.entity.Board;
import com.keduit.show.entity.Member;
import com.keduit.show.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/managementMember/list")
    public String managementMemberList(Model model) throws Exception {
        List<MemberListDTO> memberListDTOS = memberService.findMembers();


        model.addAttribute("memberListDTOS", memberListDTOS);
        return "admin/memberList";
    }

    @GetMapping("/managementMember/delete")
    public String managementMemberDelete(@RequestParam("num") Long num) throws Exception {
        memberService.deleteMemberByNum(num);
        return "redirect:/admin/managementMember/list";
    }

    @PostMapping("/managementMember/editMember")
    public String managementMemberEdit(@ModelAttribute MemberListDTO memberListDTO, Model model) throws Exception {
        memberService.updateMemberByAdmin(memberListDTO);

        return "redirect:/admin/managementMember/list";
    }

    @GetMapping({"/managementBoard/list", "managementBoard/list/{page}"})
    public String managementBoardList(BoardSearchDTO boardSearchDTO, @PathVariable("page") Optional<Integer> page, Model model, Principal principal) {

        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        Page<Board> boards = boardService.getBoardsPage(boardSearchDTO, pageable);

        model.addAttribute("boards", boards);
        model.addAttribute("maxPage", 10);
        model.addAttribute("boardSearchDTO", boardSearchDTO);

        return "admin/boardList";

    }

    @GetMapping("/managementBoard/delete")
    public String managementBoardDelete(@RequestParam("num") Long num) throws Exception {
        boardService.deleteBoard(num);
        return "redirect:/admin/managementBoard/list";
    }



}
