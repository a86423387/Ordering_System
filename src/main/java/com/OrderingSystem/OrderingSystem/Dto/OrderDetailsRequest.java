package com.OrderingSystem.OrderingSystem.Dto;

import lombok.Data;

@Data
public class OrderDetailsRequest {
    private Long dishId;
    private int quantity;
}
