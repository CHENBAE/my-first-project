package com.example.adminsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.entity.Announcement;
import com.example.adminsystem.mapper.AnnouncementMapper;
import com.example.adminsystem.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Override
    public Page<Announcement> page(int current, int size, String title, Integer publisherId) {
        Page<Announcement> page = new Page<>(current, size);
        QueryWrapper<Announcement> wrapper = new QueryWrapper<>();
        if (title != null && !title.isEmpty()) {
            wrapper.like("title", title);
        }
        if (publisherId != null) {
            wrapper.eq("publisher_id", publisherId);
        }
        wrapper.orderByDesc("publish_time");
        return announcementMapper.selectPage(page, wrapper);
    }

    @Override
    public List<Announcement> list() {
        QueryWrapper<Announcement> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("publish_time");
        return announcementMapper.selectList(wrapper);
    }

    @Override
    public Announcement getById(Integer id) {
        return announcementMapper.selectById(id);
    }

    @Override
    public boolean save(Announcement announcement) {
        return announcementMapper.insert(announcement) > 0;
    }

    @Override
    public boolean update(Announcement announcement) {
        return announcementMapper.updateById(announcement) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return announcementMapper.deleteById(id) > 0;
    }
}