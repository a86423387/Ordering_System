package com.OrderingSystem.OrderingSystem.Repository;

import com.OrderingSystem.OrderingSystem.Entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {
    // 只需繼承JpaRepository即可擁有基本操作
}
