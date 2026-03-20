package com.example.adminsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.common.Result;
import com.example.adminsystem.entity.Department;
import com.example.adminsystem.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/page")
    public Result page(@RequestParam int current, @RequestParam int size, @RequestParam(required = false) String departmentName) {
        Page<Department> page = departmentService.page(current, size, departmentName);
        return Result.success(page);
    }

    @GetMapping("/list")
    public Result list() {
        List<Department> list = departmentService.list();
        return Result.success(list);
    }

    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Integer id) {
        Department department = departmentService.getById(id);
        return Result.success(department);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Department department) {
        boolean success = departmentService.save(department);
        return success ? Result.success() : Result.error("保存失败");
    }

    @PutMapping("/update")
    public Result update(@RequestBody Department department) {
        boolean success = departmentService.update(department);
        return success ? Result.success() : Result.error("更新失败");
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = departmentService.delete(id);
        return success ? Result.success() : Result.error("删除失败");
    }
}