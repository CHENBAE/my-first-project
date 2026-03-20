package com.example.adminsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.entity.TimeNode;
import com.example.adminsystem.mapper.TimeNodeMapper;
import com.example.adminsystem.service.TimeNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeNodeServiceImpl implements TimeNodeService {

    @Autowired
    private TimeNodeMapper timeNodeMapper;

    @Override
    public Page<TimeNode> page(int current, int size, String event, String type) {
        Page<TimeNode> page = new Page<>(current, size);
        QueryWrapper<TimeNode> wrapper = new QueryWrapper<>();
        if (event != null && !event.isEmpty()) {
            wrapper.like("event", event);
        }
        if (type != null && !type.isEmpty()) {
            wrapper.eq("type", type);
        }
        wrapper.orderByAsc("month_num", "day_num");
        return timeNodeMapper.selectPage(page, wrapper);
    }

    @Override
    public List<TimeNode> list() {
        QueryWrapper<TimeNode> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("month_num", "day_num");
        return timeNodeMapper.selectList(wrapper);
    }

    @Override
    public TimeNode getById(Integer id) {
        return timeNodeMapper.selectById(id);
    }

    @Override
    public boolean save(TimeNode timeNode) {
        return timeNodeMapper.insert(timeNode) > 0;
    }

    @Override
    public boolean update(TimeNode timeNode) {
        return timeNodeMapper.updateById(timeNode) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return timeNodeMapper.deleteById(id) > 0;
    }
}