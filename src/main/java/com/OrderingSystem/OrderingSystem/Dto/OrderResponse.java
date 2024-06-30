package com.OrderingSystem.OrderingSystem.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private Long orderId;
}
