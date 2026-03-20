package com.example.adminsystem.controller;

import com.example.adminsystem.common.Result;
import com.example.adminsystem.entity.User;
import com.example.adminsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        User user = userService.login(username, password);
        if (user != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("token", username); // 简单使用用户名作为token
            data.put("user", user);
            return Result.success(data);
        } else {
            return Result.error("用户名或密码错误");
        }
    }

    @GetMapping("/info")
    public Result getUserInfo(@RequestParam String token) {
        User user = userService.getByUsername(token);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.unauth();
        }
    }

    @PostMapping("/logout")
    public Result logout() {
        return Result.success();
    }
}