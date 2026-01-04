package com.example.library.service;

import com.example.library.dto.Category;

import java.util.List;
import java.util.Map;

public interface DashboardService {
    Map<String, Object> getOverviewStats();
    
    Map<String, Object> getBorrowTrend(String period);
    
    List<Map<String, Object>> getPopularBooks(Integer limit);
    
    List<Map<String, Object>> getUserBorrowStats();
    
    List<Map<String, Object>> getCategoryStats();
}