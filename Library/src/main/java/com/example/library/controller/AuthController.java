package com.example.library.controller;

import com.example.library.common.ApiResponse;
import com.example.library.dto.LoginRequest;
import com.example.library.dto.RegisterRequest;
import com.example.library.entity.User;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.login(loginRequest);
        if (user != null) {
            // 创建token（这里简单模拟，实际项目中应该使用JWT）
            String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." + System.currentTimeMillis();
            
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", user.getId());
            userData.put("username", user.getName());
            userData.put("name", user.getName());
            userData.put("email", user.getEmail());
            userData.put("type", user.getType());
            userData.put("createTime", user.getCreateTime());
            data.put("user", userData);
            
            return ApiResponse.success("登录成功", data);
        } else {
            return ApiResponse.error(401, "用户名或密码错误");
        }
    }
    
    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody RegisterRequest registerRequest) {
        // 检查邮箱是否已存在
        User existingUser = userService.findByEmail(registerRequest.getEmail());
        if (existingUser != null) {
            return ApiResponse.error(409, "邮箱已被注册");
        }
        
        User user = userService.register(registerRequest);
        return ApiResponse.success("注册成功", user);
    }
}