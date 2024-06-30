package com.OrderingSystem.OrderingSystem.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String type_id;
}
