package com.keduit.show.controller;

import com.keduit.show.constant.Sort;
import com.keduit.show.dto.FavoriteSearchDTO;
import com.keduit.show.entity.Favorite;
import com.keduit.show.entity.Member;
import com.keduit.show.entity.Showing;
import com.keduit.show.service.FavoriteService;
import com.keduit.show.service.MemberService;
import com.keduit.show.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class FavoriteController {

    private final MemberService memberService;
    private final ShowService showService;
    private final FavoriteService favoriteService;

    //로그인된 유저의 즐겨찾기 페이지 조회
//    @GetMapping(value = "/favorites")
//    public String favorite(Principal principal, Model model) {
//        Member member = memberService.findMember(principal.getName());
//        List<Favorite> favorites = member.getFavorites();
//        List<Showing> showings = favorites.stream().map(favorite -> favorite.getShowing()).collect(Collectors.toList());
//        model.addAttribute("showings", showings);
//        return "show/favorite"; //즐겨찾기 페이지
//    }

    //로그인된 유저의 즐겨찾기 리스트 조회 (필터, 검색, 정렬)
    @GetMapping({"/favorites", "/favorites/{page}"})
    public String favorite(FavoriteSearchDTO searchDTO, @PathVariable("page")Optional<Integer> page,
                           Model model, Principal principal) {
        if(searchDTO.getSort() == null){
            searchDTO.setSort(Sort.DEFAULT);
        }
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);

        Member member = memberService.findMember(principal.getName());
        Page<Favorite> favorites = favoriteService.getFavoriteFilterPage(searchDTO, pageable, member.getNum());
        model.addAttribute("favorites", favorites);
        model.addAttribute("searchDTO", searchDTO);
        model.addAttribute("maxPage", 5);
        return "show/favorite";
    }

    //공연상세 페이지에서 즐겨찾기 추가 요청
    @PostMapping(value = "/favorite")
    public String addFavorite(Principal principal, @RequestParam("mt20id") String mt20id) {
        Member member = memberService.findMember(principal.getName());
        Showing showing = showService.findOne(mt20id);

        Favorite favorite = favoriteService.findOne(member.getNum(), mt20id);
        //찜이 안되어 잇을 경우 추가
        if(favorite == null) {
            Favorite newFavorite = new Favorite();
            newFavorite.setShowing(showing);
            newFavorite.setMember(member); //오류원인 -> 공연 상세 페이지에서 즐겨찾기 버튼 누른 회원을 가져오지 못함
            member.addFavorite(newFavorite);
            favoriteService.save(newFavorite);
        }else{ //찜이 되어 잇을 경우 취소
            member.removeFavorite(favorite);
            favoriteService.remove(favorite.getNum());
        }
        return "redirect:/show/" + mt20id; //상세페이지도 재요청
    }

    //즐겨찾기 페이지에서 즐겨찾기 취소 요청
    @PostMapping(value = "/favorite/{mt20id}/cancel")
    public String cancelFavorite(Principal principal, @PathVariable("mt20id") String mt20id) {
        Member member = memberService.findMember(principal.getName());
        Favorite favorite = favoriteService.findOne(member.getNum(), mt20id);
        member.removeFavorite(favorite);
        favoriteService.remove(favorite.getNum());
        return "redirect:/favorites"; //즐겨찾기 페이지로 재 요청
    }
}
