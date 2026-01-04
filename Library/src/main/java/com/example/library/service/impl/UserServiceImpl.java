package com.example.library.service.impl;

import com.example.library.common.PageResponse;
import com.example.library.dto.LoginRequest;
import com.example.library.dto.RegisterRequest;
import com.example.library.entity.User;
import com.example.library.mapper.UserMapper;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User login(LoginRequest loginRequest) {
        User user = userMapper.findByUsername(loginRequest.getUsername());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return user;
        }
        return null;
    }
    
    @Override
    public User register(RegisterRequest registerRequest) {
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPhone(registerRequest.getPhone());
        user.setPassword(registerRequest.getPassword());
        user.setType(registerRequest.getType());
        user.setStatus("active");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        userMapper.insert(user);
        return user;
    }
    
    @Override
    public PageResponse<User> getUsers(int page, int size, String keyword, String type) {
        int offset = (page - 1) * size;
        List<User> userList;
        long total;
        
        if (StringUtils.hasText(type) && StringUtils.hasText(keyword)) {
            userList = userMapper.findByPageAndKeywordAndType(offset, size, keyword, type);
            total = userMapper.countByKeywordAndType(keyword, type);
        } else if (StringUtils.hasText(keyword)) {
            userList = userMapper.findByPageAndKeyword(offset, size, keyword);
            total = userMapper.countByKeyword(keyword);
        } else {
            userList = userMapper.findByPage(offset, size);
            total = userMapper.countAll();
        }
        
        return new PageResponse<>(userList, total, page, size);
    }
    
    @Override
    public User getUserById(Long id) {
        return userMapper.findById(id);
    }
    
    @Override
    public User createUser(User user) {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
        return user;
    }
    
    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userMapper.findById(id);
        if (existingUser != null) {
            user.setId(id);
            user.setUpdateTime(LocalDateTime.now());
            userMapper.update(user);
            return user;
        }
        return null;
    }
    
    @Override
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }
    
    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
    
    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }
}