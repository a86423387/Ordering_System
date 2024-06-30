package com.OrderingSystem.OrderingSystem.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dish_type")
@Data // 自動生成getter、setter、toString、equals、hashCode
public class DishTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public DishTypeEntity() {
    }

    public DishTypeEntity(String name) {
        this.name = name;
    }
}
