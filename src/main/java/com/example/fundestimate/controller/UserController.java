package com.example.fundestimate.controller;

import com.example.fundestimate.entity.User;
import com.example.fundestimate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        String username = params.get("username");
        String password = params.get("password");
        
        User user = userService.login(username, password);
        if (user != null) {
            result.put("success", true);
            result.put("userId", user.getId());
            result.put("username", user.getUsername());
            result.put("nickname", user.getNickname());
        } else {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
        }
        return result;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        String username = params.get("username");
        String password = params.get("password");
        String nickname = params.get("nickname");
        
        User user = userService.register(username, password, nickname);
        if (user != null) {
            result.put("success", true);
            result.put("userId", user.getId());
        } else {
            result.put("success", false);
            result.put("message", "用户名已存在");
        }
        return result;
    }
}
