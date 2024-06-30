package com.OrderingSystem.OrderingSystem.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class OrderDetailId implements Serializable {
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "dno")
    private Integer dno;
}