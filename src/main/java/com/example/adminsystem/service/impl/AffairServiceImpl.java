package com.example.adminsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.entity.Affair;
import com.example.adminsystem.mapper.AffairMapper;
import com.example.adminsystem.service.AffairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AffairServiceImpl implements AffairService {

    @Autowired
    private AffairMapper affairMapper;

    @Override
    public Page<Affair> page(int current, int size, String title, Integer initiatorId) {
        Page<Affair> page = new Page<>(current, size);
        QueryWrapper<Affair> wrapper = new QueryWrapper<>();
        if (title != null && !title.isEmpty()) {
            wrapper.like("title", title);
        }
        if (initiatorId != null) {
            wrapper.eq("initiator_id", initiatorId);
        }
        wrapper.orderByDesc("affair_time");
        return affairMapper.selectPage(page, wrapper);
    }

    @Override
    public List<Affair> list() {
        QueryWrapper<Affair> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("affair_time");
        return affairMapper.selectList(wrapper);
    }

    @Override
    public Affair getById(Integer id) {
        return affairMapper.selectById(id);
    }

    @Override
    public boolean save(Affair affair) {
        return affairMapper.insert(affair) > 0;
    }

    @Override
    public boolean update(Affair affair) {
        return affairMapper.updateById(affair) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return affairMapper.deleteById(id) > 0;
    }
}