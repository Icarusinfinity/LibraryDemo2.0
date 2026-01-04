package com.example.library.service;

import com.example.library.common.PageResponse;
import com.example.library.dto.LoginRequest;
import com.example.library.dto.RegisterRequest;
import com.example.library.entity.User;

public interface UserService {
    User login(LoginRequest loginRequest);
    
    User register(RegisterRequest registerRequest);
    
    PageResponse<User> getUsers(int page, int size, String keyword, String type);
    
    User getUserById(Long id);
    
    User createUser(User user);
    
    User updateUser(Long id, User user);
    
    void deleteUser(Long id);
    
    User findByUsername(String username);
    
    User findByEmail(String email);
}