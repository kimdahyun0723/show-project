package com.keduit.show.controller;

import com.keduit.show.dto.OrderDTO;
import com.keduit.show.entity.Order;
import com.keduit.show.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDTO orderDTO, BindingResult bindingResult, Principal principal) {

        if(bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors) {
                errors.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity(errors.toString(), HttpStatus.BAD_GATEWAY);
        }

        String id = principal.getName();
        Long orderNum;

        try {
            orderNum = orderService.order(orderDTO, id);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity(orderDTO, HttpStatus.OK);

    }

    @GetMapping({"/orders", "/orders/{page}"})
    public String orderList(@PathVariable("page")Optional<Integer> page, Principal principal, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,4);

        Page<Order> orderPage = orderService.getOrderList(principal.getName(), pageable);


        model.addAttribute("orderPage", orderPage);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "reservation/reservationList";
    }

    @PostMapping("/order/{orderNum}/cancel")
    public @ResponseBody ResponseEntity orderCancel(@PathVariable Long orderNum, Principal principal) {
        orderService.cancelOrder(orderNum);

        return new ResponseEntity(orderNum, HttpStatus.OK);
    }

}
