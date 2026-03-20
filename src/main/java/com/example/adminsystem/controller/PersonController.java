package com.example.adminsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.common.Result;
import com.example.adminsystem.entity.Person;
import com.example.adminsystem.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/page")
    public Result page(@RequestParam int current, @RequestParam int size, 
                      @RequestParam(required = false) String name, 
                      @RequestParam(required = false) Integer departmentId) {
        Page<Person> page = personService.page(current, size, name, departmentId);
        return Result.success(page);
    }

    @GetMapping("/list")
    public Result list() {
        List<Person> list = personService.list();
        return Result.success(list);
    }

    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Integer id) {
        Person person = personService.getById(id);
        return Result.success(person);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Person person) {
        boolean success = personService.save(person);
        return success ? Result.success() : Result.error("保存失败");
    }

    @PutMapping("/update")
    public Result update(@RequestBody Person person) {
        boolean success = personService.update(person);
        return success ? Result.success() : Result.error("更新失败");
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = personService.delete(id);
        return success ? Result.success() : Result.error("删除失败");
    }
}