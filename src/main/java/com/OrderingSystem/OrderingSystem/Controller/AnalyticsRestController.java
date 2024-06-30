package com.OrderingSystem.OrderingSystem.Controller;

import com.OrderingSystem.OrderingSystem.Dto.AnalyticsResponse;
import com.OrderingSystem.OrderingSystem.Service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsRestController {
    private final AnalyticsService analyticsService;

    @GetMapping
    public ResponseEntity<AnalyticsResponse> getData(@RequestParam("date") LocalDate date) {
        return ResponseEntity.ok(analyticsService.getData(date));
    }
}
