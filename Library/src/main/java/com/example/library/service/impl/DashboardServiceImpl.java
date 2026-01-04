package com.example.library.service.impl;

import com.example.library.mapper.DashboardMapper;
import com.example.library.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {
    
    @Autowired
    private DashboardMapper dashboardMapper;
    
    @Override
    public Map<String, Object> getOverviewStats() {
        return dashboardMapper.getOverviewStats();
    }
    
    @Override
    public Map<String, Object> getBorrowTrend(String period) {
        // 默认为月
        if (period == null) {
            period = "month";
        }
        List<Map<String, Object>> trendData = dashboardMapper.getBorrowTrend(period);
        
        Map<String, Object> result = new HashMap<>();
        result.put("labels", trendData.stream().map(map -> map.get("label")).collect(Collectors.toList()));
        result.put("datasets", Arrays.asList(
            Map.of(
                "label", "月借阅量",
                "data", trendData.stream().map(map -> map.get("value")).collect(Collectors.toList())
            )
        ));
        return result;
    }
    
    @Override
    public List<Map<String, Object>> getPopularBooks(Integer limit) {
        if (limit == null) {
            limit = 10; // 默认返回10条
        }
        return dashboardMapper.getPopularBooks(limit);
    }
    
    @Override
    public List<Map<String, Object>> getUserBorrowStats() {
        return dashboardMapper.getUserBorrowStats();
    }
    
    @Override
    public List<Map<String, Object>> getCategoryStats() {
        return dashboardMapper.getCategoryStats();
    }
}