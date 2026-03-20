package com.example.adminsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.entity.Person;
import com.example.adminsystem.mapper.PersonMapper;
import com.example.adminsystem.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonMapper personMapper;

    @Override
    public Page<Person> page(int current, int size, String name, Integer departmentId) {
        Page<Person> page = new Page<>(current, size);
        QueryWrapper<Person> wrapper = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like("name", name);
        }
        if (departmentId != null) {
            wrapper.eq("department_id", departmentId);
        }
        return personMapper.selectPage(page, wrapper);
    }

    @Override
    public List<Person> list() {
        return personMapper.selectList(null);
    }

    @Override
    public Person getById(Integer id) {
        return personMapper.selectById(id);
    }

    @Override
    public boolean save(Person person) {
        return personMapper.insert(person) > 0;
    }

    @Override
    public boolean update(Person person) {
        return personMapper.updateById(person) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return personMapper.deleteById(id) > 0;
    }
}