package com.OrderingSystem.OrderingSystem.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class AnalyticsResponse {
    private Integer totalSales;
    private List<HourlySales> hourlySales;
    private List<DishRanking> dishRanking;

    @Data
    @AllArgsConstructor
    public static class HourlySales {
        private Integer hour;
        private Integer count;
        private Double total;
    }

    @Data
    @AllArgsConstructor
    public static class DishRanking {
        private String name;
        private Integer count;
    }
}
