package com.keduit.show.controller;

import com.keduit.show.constant.Sort;
import com.keduit.show.dto.BoardSearchDTO;
import com.keduit.show.dto.MemberListDTO;
import com.keduit.show.dto.MemberSearchDTO;
import com.keduit.show.dto.ShowSearchDTO;
import com.keduit.show.entity.Board;
import com.keduit.show.entity.Member;
import com.keduit.show.entity.Showing;
import com.keduit.show.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "admin/API";
    }

    @GetMapping("/managementAPI")
    public String managementAPI() throws Exception {

        return "admin/API";
    }

    @GetMapping({"/managementAPI/detail", "/managementAPI/detail/{page}"})
    public String showList(ShowSearchDTO showSearchDTO,
                           @PathVariable("page")Optional<Integer> page,
                           Model model) {
        if (showSearchDTO.getSort() == null) {
            showSearchDTO.setSort(Sort.DEFAULT); // 기본값 설정
        }
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<Showing> shows = showService.getShowFilterPage(showSearchDTO, pageable);
        model.addAttribute("showLists", shows);
        model.addAttribute("showSearchDTO", showSearchDTO);
        model.addAttribute("maxPage", 5);
        return "admin/showingList"; //공연목록페이지
    }

    @GetMapping("/managementAPI/update")
    public String managementAPIUpdate(RedirectAttributes redirectAttributes) throws Exception {
        int showCount = showApiService.saveShow(); //공연목록
        showApiService.saveShowFacility(); //시설목록

        redirectAttributes.addFlashAttribute("showCount", showCount);
        return "redirect:/admin/managementAPI";
    }

    @PostMapping("/managementAPI/delete")
    public String managementAPIDelete(@RequestParam("standard") Integer standard, RedirectAttributes redirectAttributes) throws Exception {
        int deleteShowCount = showApiService.deleteShow(standard);

        redirectAttributes.addFlashAttribute("deleteShowCount", deleteShowCount);

        return "redirect:/admin/managementAPI";
    }

    @DeleteMapping("/managementAPI/deleteShow/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteShow(@PathVariable("id") String showId) {
        try {
            System.out.println(showId + " =====================================showid");
            showService.deleteShow(showId); // 서비스에서 삭제 처리
            return ResponseEntity.ok().build(); // 성공 응답
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 에러 응답
        }
    }


    @GetMapping({"/managementMember/list","/managementMember/list/{page}"})
    public String managementMemberList(MemberSearchDTO memberSearchDTO, Model model, @PathVariable("page")Optional<Integer> page, Principal principal) throws Exception {

        Pageable pageable = PageRequest.of(page.orElse(0), 10);

        Page<Member> memberPage = memberService.getMemberPage(memberSearchDTO, pageable);

        model.addAttribute("memberPage", memberPage);
        model.addAttribute("memberSearchDTO", memberSearchDTO);
        model.addAttribute("maxPage", 10);


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

        Pageable pageable = PageRequest.of(page.orElse(0), 10);
        Page<Board> boards = boardService.getBoardsPage(boardSearchDTO, pageable);

        model.addAttribute("boards", boards);
        model.addAttribute("boardSearchDTO", boardSearchDTO);
        model.addAttribute("maxPage", 10);

        return "admin/boardList";

    }

    @GetMapping("/managementBoard/delete")
    public String managementBoardDelete(@RequestParam("num") Long num) throws Exception {
        boardService.deleteBoard(num);
        return "redirect:/admin/managementBoard/list";
    }



}
