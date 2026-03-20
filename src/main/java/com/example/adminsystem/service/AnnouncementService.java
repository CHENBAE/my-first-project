package com.example.adminsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.entity.Announcement;

import java.util.List;

public interface AnnouncementService {
    Page<Announcement> page(int current, int size, String title, Integer publisherId);
    List<Announcement> list();
    Announcement getById(Integer id);
    boolean save(Announcement announcement);
    boolean update(Announcement announcement);
    boolean delete(Integer id);
}