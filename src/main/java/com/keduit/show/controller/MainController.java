package com.keduit.show.controller;

import com.keduit.show.dto.ShowSearchDTO;
import com.keduit.show.entity.Showing;
import com.keduit.show.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ShowService showService;

    @GetMapping("/")
    public String main(ShowSearchDTO showSearchDTO, Model model) {
        //일주일 전후 사이 공연 리스트
        List<Showing> shows = showService.getShow();
        model.addAttribute("exhibitionList", shows);

        //장르별 상영중인 공연 리스트
        List<Showing> showGenre = showService.getShowFilterGenre(showSearchDTO);
        model.addAttribute("genreList", showGenre);
        model.addAttribute("showSearchDTO", showSearchDTO);
        return "main";
    }


}
