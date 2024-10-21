package com.keduit.show.repository;

import com.keduit.show.entity.Favorite;
import com.keduit.show.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o " + "where o.member.id = :id" + " order by o.regTime desc")
    List<Order> findOrder(@Param("id") String id, Pageable pageable);

    @Query("select count(o) from Order o where o.member.id = :id")
    Long countOrder(@Param("id") String id);

    //특정회원의 주문 리스트
    @Query("select o from Order o where o.member.num = :memberNum")
    List<Order> findOrderByMemberNum(@Param("memberNum") Long memberNum);
}
