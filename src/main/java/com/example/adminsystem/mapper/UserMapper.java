package com.example.adminsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.adminsystem.entity.User;

public interface UserMapper extends BaseMapper<User> {
    User selectByUsername(String username);
}