package com.keduit.show.entity;

import com.keduit.show.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name="orders")
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_num")
    private Long num;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mumber_num")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mt20id")
    private Showing showing;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private int count;

    public static Order createOrder(Member member, Showing showing, int count){
        Order order = new Order();
        order.setMember(member);
        order.setShowing(showing);
        order.setStatus(OrderStatus.ORDER);
        order.setCount(count);

        return order;
    }

    public void cancelOrder() {
        this.status = OrderStatus.CANCEL;

        showing.cancel(this.count);
    }

}
