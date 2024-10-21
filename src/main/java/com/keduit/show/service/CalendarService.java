package com.keduit.show.service;

import com.keduit.show.entity.Favorite;
import com.keduit.show.entity.Order;
import com.keduit.show.repository.FavoriteRepository;
import com.keduit.show.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {

    private final FavoriteRepository favoriteRepository;
    private final OrderRepository orderRepository;

    //즐겨찾기 리스트
    public List<Map<String, Object>> getFavoriteList(Long memberNum){
        List<Map<String, Object>> eventFavoriteList = new ArrayList<Map<String, Object>>();
        List<Favorite> favorites = favoriteRepository.findReviewByMemberNum(memberNum);

        for (Favorite favorite : favorites) {
            log.info("Showing: {}", favorite.getShowing().getPrfnm());
        }

        //title, start, end 는 필수로 넣어야함
        for (Favorite favorite : favorites) {
            Map<String, Object> eventFavorite = new HashMap<String, Object>();
            eventFavorite.put("title", favorite.getShowing().getPrfnm()); //공연이름
            eventFavorite.put("start", favorite.getShowing().getPrfpdfrom().toString()); //공연시작일
            eventFavorite.put("end", favorite.getShowing().getPrfpdto().plusDays(1).toString()); //공연종료일
            eventFavorite.put("url", "/show/" + favorite.getShowing().getMt20id()); //상세페이지로 이동
            eventFavoriteList.add(eventFavorite);
        }
        System.out.println("맵---------------" + eventFavoriteList);
        return eventFavoriteList;
    }

    //주문 리스트
    public List<Map<String, Object>> getOrderList(Long memberNum){
        List<Map<String, Object>> eventOrderList = new ArrayList<Map<String, Object>>();
        List<Order> orders = orderRepository.findOrderByMemberNum(memberNum);

        for (Order order : orders) {
            log.info("Order: {}", order.getShowing().getPrfnm());
        }

        //title, start, end 는 필수로 넣어야함
        for (Order order : orders) {
            Map<String, Object> eventOrder = new HashMap<String, Object>();
            eventOrder.put("title", order.getShowing().getPrfnm()); //공연이름
            eventOrder.put("start", order.getShowing().getPrfpdfrom().toString()); //공연시작일
            eventOrder.put("end", order.getShowing().getPrfpdto().plusDays(1).toString()); //공연종료일
            eventOrder.put("url", "/show/" + order.getShowing().getMt20id()); //상세페이지로 이동
            eventOrderList.add(eventOrder);
        }
        System.out.println("맵---------------" + eventOrderList);
        return eventOrderList;
    }
}
