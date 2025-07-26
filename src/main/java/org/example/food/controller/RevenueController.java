package org.example.food.controller;

import org.example.food.DTO.RevenueByCategoryDto;
import org.example.food.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/admin/revenue")
public class RevenueController {
    @Autowired
    RevenueService revenueService;
    @GetMapping("/{restaurantId}/category")
    public ResponseEntity<List<Map<String, Object>>> getRevenueByCategory(
            @PathVariable Long restaurantId) {
        return ResponseEntity.ok(revenueService.getRevenueByCategoryList(restaurantId));
    }
    @GetMapping("/{restaurantId}/daily")
    public ResponseEntity<List<Map<String, Object>>> getDaily(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(revenueService.getDailyRevenue(restaurantId));
    }

    @GetMapping("/{restaurantId}/monthly")
    public ResponseEntity<List<Map<String, Object>>> getMonthly(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(revenueService.getMonthlyRevenue(restaurantId));
    }

    @GetMapping("/{restaurantId}/yearly")
    public ResponseEntity<List<Map<String, Object>>> getYearly(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(revenueService.getYearlyRevenue(restaurantId));
    }
    @GetMapping("/api/revenue/category-wise/{restaurantId}")
    public ResponseEntity<List<RevenueByCategoryDto>> getRevenueByCategoryWise(
            @PathVariable Long restaurantId) {
        return ResponseEntity.ok(revenueService.getCategoryWiseRevenue(restaurantId));
    }


}
