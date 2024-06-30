package com.OrderingSystem.OrderingSystem.Repository;

import com.OrderingSystem.OrderingSystem.Entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends JpaRepository<DishEntity, Long> {
    // 這裡不用寫程式碼，只要將DishRepository繼承JpaRepository就可有基本的資料庫新增讀寫功能
}
