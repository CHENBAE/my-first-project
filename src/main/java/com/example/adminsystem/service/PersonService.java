package com.example.adminsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.entity.Person;

import java.util.List;

public interface PersonService {
    Page<Person> page(int current, int size, String name, Integer departmentId);
    List<Person> list();
    Person getById(Integer id);
    boolean save(Person person);
    boolean update(Person person);
    boolean delete(Integer id);
}