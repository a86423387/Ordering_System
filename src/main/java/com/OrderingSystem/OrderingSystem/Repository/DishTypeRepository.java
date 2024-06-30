package com.OrderingSystem.OrderingSystem.Repository;

import com.OrderingSystem.OrderingSystem.Entity.DishTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DishTypeRepository extends JpaRepository<DishTypeEntity, Long> {
    Optional<DishTypeEntity> findByName(String name);
}
