package com.keduit.show.controller;

import com.keduit.show.dto.ShowSearchDTO;
import com.keduit.show.entity.Showing;
import com.keduit.show.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private ShowService showService;

    @GetMapping("/")
    public String main(@RequestParam(required = false) String genre, Model model) {
        // 일주일 전후 사이 공연 리스트
        List<Showing> shows = showService.getShow();
        model.addAttribute("exhibitionList", shows);

        List<Showing> showingList = showService.getAllShowList();

        // 장르가 제공된 경우 필터링
        List<Showing> filteredShows;
        if (genre != null && !genre.isEmpty()) {
            filteredShows = showingList.stream()
                    .filter(showing -> genre.equalsIgnoreCase(showing.getGenrenm().toString()))
                    .limit(5)
                    .collect(Collectors.toList());
        } else {
            // 기본적으로 "dance" 장르의 전시를 가져옴
            filteredShows = showingList.stream()
                    .filter(showing -> "POPULAR_MUSIC".equalsIgnoreCase(showing.getGenrenm().toString()))
                    .limit(5)
                    .collect(Collectors.toList());
        }

        model.addAttribute("showList", filteredShows);
        model.addAttribute("showSearchDTO", new ShowSearchDTO()); // 필요시 DTO 추가
        return "main"; // main.html로 반환
    }

    @GetMapping("/genreFilter")
    @ResponseBody // JSON으로 응답하기 위해 사용
    public String fetchShowsByGenre(@RequestParam String genre) {
        List<Showing> showingList = showService.getAllShowList();

        List<Showing> filteredShows = showingList.stream()
                .filter(showing -> genre.equalsIgnoreCase(showing.getGenrenm().toString()))
                .limit(5)
                .collect(Collectors.toList());

        // Thymeleaf 템플릿을 사용하여 HTML 문자열 생성
        StringBuilder htmlBuilder = new StringBuilder();
        for (Showing exhibition : filteredShows) {
            htmlBuilder.append("<div class='col-lg-2 col-md-4 col-sm-6 exhibition-item'>")
                    .append("<a href='/show/").append(exhibition.getMt20id()).append("'>")
                    .append("<div class='poster-wrapper'>")
                    .append("<img src='").append(exhibition.getPoster()).append("' class='poster-img' alt='Exhibition Poster'>")
                    .append("<div class='exhibition-rank'>")
                    .append("<span>").append(filteredShows.indexOf(exhibition) + 1).append("</span>") // 순위 표시
                    .append("</div>")
                    .append("<div class='exhibition-title'>").append(exhibition.getPrfnm()).append("</div>")
                    .append("<div class='exhibition-date'>").append(exhibition.getPrfpdfrom()).append(" ~ ").append(exhibition.getPrfpdto()).append("</div>")
                    .append("</div></a></div>");
        }

        return htmlBuilder.toString(); // HTML 문자열 반환
    }



}
