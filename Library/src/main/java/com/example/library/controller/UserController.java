package com.example.library.controller;

import com.example.library.common.ApiResponse;
import com.example.library.common.PageResponse;
import com.example.library.entity.User;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ApiResponse<PageResponse<User>> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String type) {
        
        PageResponse<User> users = userService.getUsers(page, size, keyword, type);
        return ApiResponse.success(users);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ApiResponse.success(user);
        } else {
            return ApiResponse.error(404, "用户不存在");
        }
    }
    
    @PostMapping
    public ApiResponse<User> createUser(@RequestBody User user) {
        // 检查邮箱是否已存在
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null) {
            return ApiResponse.error(409, "邮箱已被使用");
        }
        
        User createdUser = userService.createUser(user);
        return ApiResponse.success("创建成功", createdUser);
    }
    
    @PutMapping("/{id}")
    public ApiResponse<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return ApiResponse.success("更新成功", updatedUser);
        } else {
            return ApiResponse.error(404, "用户不存在");
        }
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.success("删除成功", null);
    }
}