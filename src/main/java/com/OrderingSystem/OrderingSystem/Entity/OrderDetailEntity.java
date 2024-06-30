package com.OrderingSystem.OrderingSystem.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
@Data
public class OrderDetailEntity {
    @EmbeddedId
    private OrderDetailId id = new OrderDetailId();

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private DishEntity dish;

    private Integer count;
    private double total;
}
