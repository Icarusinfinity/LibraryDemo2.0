package com.example.library.controller;

import com.example.library.common.ApiResponse;
import com.example.library.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    
    @Autowired
    private DashboardService dashboardService;
    
    @GetMapping("/overview")
    public ApiResponse<Map<String, Object>> getOverview() {
        Map<String, Object> overview = dashboardService.getOverviewStats();
        return ApiResponse.success(overview);
    }
    
    @GetMapping("/borrow-trend")
    public ApiResponse<Map<String, Object>> getBorrowTrend(@RequestParam(required = false) String period) {
        Map<String, Object> trend = dashboardService.getBorrowTrend(period);
        return ApiResponse.success(trend);
    }
    
    @GetMapping("/popular-books")
    public ApiResponse<List<Map<String, Object>>> getPopularBooks(@RequestParam(required = false) Integer limit) {
        List<Map<String, Object>> popularBooks = dashboardService.getPopularBooks(limit);
        return ApiResponse.success(popularBooks);
    }
    
    @GetMapping("/user-borrow-stats")
    public ApiResponse<List<Map<String, Object>>> getUserBorrowStats() {
        List<Map<String, Object>> userStats = dashboardService.getUserBorrowStats();
        return ApiResponse.success(userStats);
    }
    
    @GetMapping("/category-stats")
    public ApiResponse<List<Map<String, Object>>> getCategoryStats() {
        List<Map<String, Object>> categoryStats = dashboardService.getCategoryStats();
        return ApiResponse.success(categoryStats);
    }
}