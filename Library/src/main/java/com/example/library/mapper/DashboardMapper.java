package com.example.library.mapper;

import com.example.library.dto.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DashboardMapper {
    // 获取统计概览数据
    Map<String, Object> getOverviewStats();
    
    // 获取借阅趋势数据
    List<Map<String, Object>> getBorrowTrend(@Param("period") String period);
    
    // 获取热门图书排行
    List<Map<String, Object>> getPopularBooks(@Param("limit") Integer limit);
    
    // 获取用户借阅统计
    List<Map<String, Object>> getUserBorrowStats();
    
    // 获取图书分类统计
    List<Map<String, Object>> getCategoryStats();
}