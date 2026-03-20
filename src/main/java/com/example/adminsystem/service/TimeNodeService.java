package com.example.adminsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.entity.TimeNode;

import java.util.List;

public interface TimeNodeService {
    Page<TimeNode> page(int current, int size, String event, String type);
    List<TimeNode> list();
    TimeNode getById(Integer id);
    boolean save(TimeNode timeNode);
    boolean update(TimeNode timeNode);
    boolean delete(Integer id);
}