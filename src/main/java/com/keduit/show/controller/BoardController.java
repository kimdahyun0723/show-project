package com.keduit.show.controller;

import com.keduit.show.dto.BoardDTO;
import com.keduit.show.dto.BoardSearchDTO;
import com.keduit.show.dto.ReplyResponseDTO;
import com.keduit.show.entity.Board;
import com.keduit.show.entity.Member;
import com.keduit.show.service.BoardService;
import com.keduit.show.service.MemberService;
import com.keduit.show.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private final ReplyService replyService;

    private final MemberService memberService;

    @GetMapping({"/board", "/board/{page}"})
    public String BoardManage(BoardSearchDTO boardSearchDTO, @PathVariable("page") Optional<Integer> page, Model model, Principal principal) {

        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        Page<Board> boards = boardService.getBoardsPage(boardSearchDTO, pageable);

        model.addAttribute("boards", boards);
        model.addAttribute("maxPage", 10);
        model.addAttribute("boardSearchDTO", boardSearchDTO);
        model.addAttribute("memberId", principal == null ? "login" : principal.getName());

        return "board/board";
    }

    @GetMapping("/board/write")
    public String BoardWrite(Model model, Principal principal) {
        model.addAttribute("memberId", principal.getName());
        BoardDTO boardDTO = new BoardDTO();
        model.addAttribute("boardDTO", boardDTO);
        System.out.println("===========================boardDTo" + boardDTO.getNum());
        return "board/write";
    }

    @PostMapping("/board/write")
    public String BoardWrite(@Valid BoardDTO boardDTO, BindingResult bindingResult,
                             Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "board/write";
        }

        boardService.saveBoard(boardDTO, principal.getName());
        model.addAttribute("boardDTO", boardDTO);

        return "redirect:/board";
    }

    @PostMapping("/board/showModify/{boardId}")
    public String showBoardModify(Model model, @PathVariable("boardId") Long boardId, Principal principal) {


        Board board = boardService.getBoard(boardId);
        BoardDTO boardDTO = BoardDTO.of(board);

        Member member = memberService.findMember(principal.getName());

        String role = member.getRole().toString();

        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("memberId", principal.getName());
        model.addAttribute("role", role);
        System.out.println("boardDTO=========================" + boardDTO);

        return "board/write";
    }

    @PostMapping("/board/modify/{boardId}")
    public String boardModify(@Valid BoardDTO boardDTO, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, Model model, @PathVariable("boardId") Long boardId) {


        boardService.updateBoard(boardDTO, boardId);

        System.out.println("boardDTO=========================" + boardDTO);
        redirectAttributes.addFlashAttribute("message", "작업이 완료되었습니다.");


        return "redirect:/board";
    }


    @GetMapping("/board/boardDtl/{boardId}")
    public String BoardWrite(@PathVariable("boardId") Long boardId, Model model, Principal principal) {

        BoardDTO boardDTO = boardService.getBoardDtl(boardId);
        Board board = boardService.getBoard(boardId);
        List<ReplyResponseDTO> replyResponseDTOList = replyService.commentList(boardId);

        if (principal != null) {
            Member member = memberService.findMember(principal.getName());
            String role = member.getRole().toString();
            model.addAttribute("role", role);
        } else if (principal == null) {
            model.addAttribute("role", "false");
        }

        model.addAttribute("replys", replyResponseDTOList);
        model.addAttribute("boardDTO", boardDTO);
        String memberId = principal != null ? principal.getName() : "noOne";
        model.addAttribute("memberId", memberId);
        model.addAttribute("id", boardId);



        return "board/boardDtl";
    }

    @PostMapping("/board/delete/{boardId}")
    public String BoardDelete(@PathVariable("boardId") Long boardId, Model model, Principal principal) {
        Member member = memberService.findMember(principal.getName());


        if (!member.getRole().toString().equals("ADMIN")) {
            if (!boardService.validationBoard(boardId, principal.getName())) {
                model.addAttribute("errorMessage", "삭제 권한이 없습니다.");
                return "board/boardDtl";
            }
        }

        boardService.deleteBoard(boardId);

        return "redirect:/board";
    }


}

