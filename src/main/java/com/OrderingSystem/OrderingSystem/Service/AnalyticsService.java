package com.OrderingSystem.OrderingSystem.Service;

import com.OrderingSystem.OrderingSystem.Dto.AnalyticsResponse;
import com.OrderingSystem.OrderingSystem.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final OrderRepository orderRepository;

    public AnalyticsResponse getData(LocalDate date) {
        // 獲取總銷售額
        Integer totalSales = orderRepository.getTotalSalesAmount(date);

        // 獲取每小時銷售數據
        List<Object[]> hourlySalesList = orderRepository.getHourlySales(date);
        List<AnalyticsResponse.HourlySales> hourlySales = new ArrayList<>();
        for (Object[] obj : hourlySalesList) {
            hourlySales.add(new AnalyticsResponse.HourlySales((Integer) obj[0], (Integer) obj[1], (Integer) obj[2]));
        }

        // 獲取最熱銷餐點數據
        List<Object[]> dishRankingList = orderRepository.getTop5DishRanking(date);
        List<AnalyticsResponse.DishRanking> dishRanking = new ArrayList<>();
        for (Object[] obj : dishRankingList) {
            dishRanking.add(new AnalyticsResponse.DishRanking((String) obj[0], (Integer) obj[1]));
        }

        return new AnalyticsResponse(totalSales, hourlySales, dishRanking);
    }
}
