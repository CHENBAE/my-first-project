package com.example.adminsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.entity.Meeting;

import java.util.List;

public interface MeetingService {
    Page<Meeting> page(int current, int size, String title, Integer hostId);
    List<Meeting> list();
    Meeting getById(Integer id);
    boolean save(Meeting meeting);
    boolean update(Meeting meeting);
    boolean delete(Integer id);
}