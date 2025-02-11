package com.OrderingSystem.OrderingSystem.Repository;

import com.OrderingSystem.OrderingSystem.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Override
    @Query("SELECT o FROM OrderEntity o ORDER BY o.dateTime DESC")
    List<OrderEntity> findAll();

    @Query("SELECT SUM(o.totalPrice) FROM OrderEntity o WHERE DATE(o.dateTime) = :date")
    Integer getTotalSalesAmount(@Param("date") LocalDate date);

    @Query(value = "SELECT d.name, t.count " +
            "FROM ( " +
            "    SELECT od.dish_id as dish_id, SUM(od.count) as count " +
            "    FROM _order o " +
            "    LEFT JOIN order_detail od ON o.id = od.order_id " +
            "    WHERE DATE(o.date_time) = :date " +
            "    GROUP BY od.dish_id " +
            ") t " +
            "LEFT JOIN dish_entity d ON t.dish_id = d.id " +
            "ORDER BY t.count DESC " +
            "LIMIT 5",
            nativeQuery = true)
    List<Object[]> getTop5DishRanking(@Param("date") LocalDate date);

    @Query(value = "SELECT EXTRACT(HOUR FROM o.date_time) as hour, COUNT(*) as saleCount, SUM(o.total_price) as totalSales " +
            "FROM _order o " +
            "WHERE DATE(o.date_time) = :date " +
            "GROUP BY EXTRACT(HOUR FROM o.date_time)",
            nativeQuery = true)
    List<Object[]> getHourlySales(@Param("date") LocalDate date);
}
