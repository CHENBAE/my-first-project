package com.example.adminsystem.service;

import com.example.adminsystem.entity.User;

public interface UserService {
    User login(String username, String password);
    User getByUsername(String username);
}