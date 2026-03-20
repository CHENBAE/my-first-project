package com.example.adminsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.entity.Department;
import com.example.adminsystem.mapper.DepartmentMapper;
import com.example.adminsystem.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Page<Department> page(int current, int size, String departmentName) {
        Page<Department> page = new Page<>(current, size);
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        if (departmentName != null && !departmentName.isEmpty()) {
            wrapper.like("department_name", departmentName);
        }
        wrapper.orderByAsc("sort");
        return departmentMapper.selectPage(page, wrapper);
    }

    @Override
    public List<Department> list() {
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        return departmentMapper.selectList(wrapper);
    }

    @Override
    public Department getById(Integer id) {
        return departmentMapper.selectById(id);
    }

    @Override
    public boolean save(Department department) {
        return departmentMapper.insert(department) > 0;
    }

    @Override
    public boolean update(Department department) {
        return departmentMapper.updateById(department) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return departmentMapper.deleteById(id) > 0;
    }
}