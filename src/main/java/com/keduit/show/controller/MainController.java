package com.keduit.show.controller;

import com.keduit.show.dto.ShowSearchDTO;
import com.keduit.show.entity.Showing;
import com.keduit.show.service.ShowApiService;
import com.keduit.show.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private ShowService showService;
    @Autowired
    private ShowApiService showApiService;

    //공연 데이터 처음 저장
//    @PostMapping("/")
//    @ResponseBody
//    public ResponseEntity<String> initialize(){
//        try {
//            showApiService.init();
//            return ResponseEntity.ok("초기 데이터 저장 성공");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("초기 데이터 저장 실패");
//        }
//    }

    // 메인 페이지에서 처음 8개의 전시회만 보여줌
    @GetMapping("/")
    public String main(Model model) {

        // 전체 전시회 목록
        List<Showing> shows = showService.getShow();

        // 처음에 8개만 잘라서 전달
        List<Showing> limitedShows = shows.size() > 8 ? shows.subList(0, 8) : shows;
        model.addAttribute("exhibitionList", limitedShows); // 처음 8개의 전시회
        model.addAttribute("totalShows", shows.size()); // 전체 전시회 수

        // 장르 필터링을 위한 데이터
        List<Showing> showingList = showService.getAllShowList();
        List<Showing> filteredShows = showingList.stream()
            .filter(showing -> "dance".equalsIgnoreCase(showing.getGenrenm().toString()))
            .limit(5) // 기본적으로 "dance" 장르 5개 가져오기
            .collect(Collectors.toList());

        model.addAttribute("showList", filteredShows);
        model.addAttribute("showSearchDTO", new ShowSearchDTO()); // 필요시 DTO 추가

        return "main"; // main.html로 반환ㅇ
    }

    @GetMapping("/loadMoreShows")
    @ResponseBody
    public String loadMoreShows(@RequestParam int offset, @RequestParam int limit) {
        List<Showing> showingList = showService.getShow();

        // offset부터 limit개까지의 전시회만 가져옴
        List<Showing> limitedShows = showingList.stream()
                .skip(offset) // 시작 지점부터 건너뜀
                .limit(limit) // limit 개수만큼 가져옴
                .collect(Collectors.toList());

        // Thymeleaf 템플릿을 사용하여 HTML 문자열을 생성
        StringBuilder htmlBuilder = new StringBuilder();
        for (Showing exhibition : limitedShows) {
            htmlBuilder.append("<div class='col-lg-3 col-md-4 col-sm-6 exhibition-item'>")
                    .append("<a href='/show/").append(exhibition.getMt20id()).append("'>")
                    .append("<div class='poster-wrapper'>")
                    .append("<img src='").append(exhibition.getPoster()).append("' class='poster-img' alt='Exhibition Poster'>")
                    .append("<div class='exhibition-title'>").append(exhibition.getPrfnm()).append("</div>")
                    .append("<div class='exhibition-date'>").append(exhibition.getPrfpdfrom()).append(" ~ ").append(exhibition.getPrfpdto()).append("</div>")
                    .append("</div></a></div>");
        }

        return htmlBuilder.toString(); // HTML 문자열 반환
    }


    // 장르 필터링에 따른 전시회 로드
    @GetMapping("/genreFilter")
    @ResponseBody
    public String fetchShowsByGenre(@RequestParam String genre) {
        List<Showing> showingList = showService.getAllShowList();

        // 선택된 장르에 맞게 필터링
        List<Showing> filteredShows = showingList.stream()
            .filter(showing -> genre.equalsIgnoreCase(showing.getGenrenm().toString()))
            .limit(5) // 최대 5개로 제한
            .collect(Collectors.toList());

        // Thymeleaf 템플릿을 사용하여 HTML 문자열을 생성
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

    @GetMapping("/error/403")
    public String error403() {
        return "forbidden";
    }
}
