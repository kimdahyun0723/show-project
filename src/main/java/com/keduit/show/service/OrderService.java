package com.keduit.show.service;

import com.keduit.show.constant.TicketStatus;
import com.keduit.show.dto.OrderDTO;
import com.keduit.show.entity.Member;
import com.keduit.show.entity.Order;
import com.keduit.show.entity.Showing;
import com.keduit.show.repository.MemberRepository;
import com.keduit.show.repository.OrderRepository;
import com.keduit.show.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ShowRepository showRepository;

    public Long order(OrderDTO orderDTO, String id) {
        Showing show = showRepository.findById(orderDTO.getShowNum()).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findById(id);

        show.removeTicket(orderDTO.getCount());
        if (show.getTicket() <= 0) {
            show.setTicketStatus(TicketStatus.SOLD_OUT);
        }

        Order order = Order.createOrder(member, show, orderDTO.getCount());
        orderRepository.save(order);
        return order.getNum();
    }

    public Page<Order> getOrderList(String id, Pageable pageable) {
        List<Order> orders = orderRepository.findOrder(id, pageable);
        Long totalCount = orderRepository.countOrder(id);

        return new PageImpl<>(orders, pageable, totalCount);
    }

    public void cancelOrder(Long orderNum) {
        Order order = orderRepository.findById(orderNum).orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }

}
