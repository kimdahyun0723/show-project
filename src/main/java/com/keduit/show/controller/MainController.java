package com.keduit.show.controller;

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
    public String main(Model model) {
        List<Showing> shows = showService.getShow();
        model.addAttribute("exhibitionList", shows);
        return "main";
    }
}
