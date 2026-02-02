package com.example.fundestimate.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.fundestimate.entity.User;
import com.example.fundestimate.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username).eq("password", password);
        return userMapper.selectOne(wrapper);
    }

    public User register(String username, String password, String nickname) {
        // 检查用户名是否已存在
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        if (userMapper.selectOne(wrapper) != null) {
            return null; // 用户名已存在
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        userMapper.insert(user);
        return user;
    }

    public User getById(Long id) {
        return userMapper.selectById(id);
    }
}
