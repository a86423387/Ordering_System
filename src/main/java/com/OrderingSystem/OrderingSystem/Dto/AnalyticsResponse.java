package com.OrderingSystem.OrderingSystem.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsResponse {

    private Integer totalSales;
    private List<HourlySales> hourlySales;
    private List<DishRanking> dishRanking;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HourlySales {
        private Integer hour;
        private Integer saleCount;
        private Integer totalSales;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DishRanking {
        private String name;
        private Integer count;
    }
}
