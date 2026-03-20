package com.example.adminsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.entity.Affair;

import java.util.List;

public interface AffairService {
    Page<Affair> page(int current, int size, String title, Integer initiatorId);
    List<Affair> list();
    Affair getById(Integer id);
    boolean save(Affair affair);
    boolean update(Affair affair);
    boolean delete(Integer id);
}