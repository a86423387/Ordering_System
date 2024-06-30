package com.OrderingSystem.OrderingSystem.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "_order")
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "pay_method")
    private String payMethod;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetailEntity> orderDetails = new ArrayList<>();

    public void addOrderDetail(OrderDetailEntity orderDetail) {
        orderDetails.add(orderDetail);
        orderDetail.setOrder(this);
    }
}
