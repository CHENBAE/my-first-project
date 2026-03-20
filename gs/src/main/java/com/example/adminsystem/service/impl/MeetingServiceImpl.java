package com.example.adminsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.entity.Meeting;
import com.example.adminsystem.mapper.MeetingMapper;
import com.example.adminsystem.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private MeetingMapper meetingMapper;

    @Override
    public Page<Meeting> page(int current, int size, String title, Integer hostId) {
        Page<Meeting> page = new Page<>(current, size);
        QueryWrapper<Meeting> wrapper = new QueryWrapper<>();
        if (title != null && !title.isEmpty()) {
            wrapper.like("title", title);
        }
        if (hostId != null) {
            wrapper.eq("host_id", hostId);
        }
        wrapper.orderByDesc("meeting_time");
        return meetingMapper.selectPage(page, wrapper);
    }

    @Override
    public List<Meeting> list() {
        QueryWrapper<Meeting> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("meeting_time");
        return meetingMapper.selectList(wrapper);
    }

    @Override
    public Meeting getById(Integer id) {
        return meetingMapper.selectById(id);
    }

    @Override
    public boolean save(Meeting meeting) {
        return meetingMapper.insert(meeting) > 0;
    }

    @Override
    public boolean update(Meeting meeting) {
        return meetingMapper.updateById(meeting) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return meetingMapper.deleteById(id) > 0;
    }
}