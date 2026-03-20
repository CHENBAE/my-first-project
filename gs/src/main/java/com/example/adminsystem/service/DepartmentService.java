package com.example.adminsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.entity.Department;

import java.util.List;

public interface DepartmentService {
    Page<Department> page(int current, int size, String departmentName);
    List<Department> list();
    Department getById(Integer id);
    boolean save(Department department);
    boolean update(Department department);
    boolean delete(Integer id);
}