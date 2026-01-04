package com.example.library.mapper;

import com.example.library.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User findById(Long id);
    
    User findByUsername(String username);
    
    List<User> findAll();
    
    List<User> findByPage(@Param("offset") int offset, @Param("size") int size);
    
    List<User> findByPageAndKeyword(@Param("offset") int offset, @Param("size") int size, @Param("keyword") String keyword);
    
    List<User> findByPageAndKeywordAndType(@Param("offset") int offset, @Param("size") int size, 
                                          @Param("keyword") String keyword, @Param("type") String type);
    
    int countAll();
    
    int countByKeyword(@Param("keyword") String keyword);
    
    int countByKeywordAndType(@Param("keyword") String keyword, @Param("type") String type);
    
    int insert(User user);
    
    int update(User user);
    
    int deleteById(Long id);
    
    User findByEmail(String email);
}