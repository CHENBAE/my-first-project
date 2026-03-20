package com.example.adminsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.entity.Leader;
import com.example.adminsystem.mapper.LeaderMapper;
import com.example.adminsystem.service.LeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderServiceImpl implements LeaderService {

    @Autowired
    private LeaderMapper leaderMapper;

    @Override
    public Page<Leader> page(int current, int size, String name, String position) {
        Page<Leader> page = new Page<>(current, size);
        QueryWrapper<Leader> wrapper = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like("name", name);
        }
        if (position != null && !position.isEmpty()) {
            wrapper.like("position", position);
        }
        return leaderMapper.selectPage(page, wrapper);
    }

    @Override
    public List<Leader> list() {
        return leaderMapper.selectList(null);
    }

    @Override
    public Leader getById(Integer id) {
        return leaderMapper.selectById(id);
    }

    @Override
    public boolean save(Leader leader) {
        return leaderMapper.insert(leader) > 0;
    }

    @Override
    public boolean update(Leader leader) {
        return leaderMapper.updateById(leader) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return leaderMapper.deleteById(id) > 0;
    }
}