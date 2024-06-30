package com.OrderingSystem.OrderingSystem.Entity;

import lombok.Data;
import javax.persistence.*;

@Data // 自動生成getter、setter、toString、equals、hashCode
@Entity
@Table(name = "dish_entity")
public class DishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private DishTypeEntity type_id;

    private String description; // 添加這個字段

    public DishTypeEntity getType() {
        return type_id;
    }
}
