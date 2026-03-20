package com.example.adminsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.entity.Leader;

import java.util.List;

public interface LeaderService {
    Page<Leader> page(int current, int size, String name, String position);
    List<Leader> list();
    Leader getById(Integer id);
    boolean save(Leader leader);
    boolean update(Leader leader);
    boolean delete(Integer id);
}