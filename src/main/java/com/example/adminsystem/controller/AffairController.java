package com.example.adminsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.common.Result;
import com.example.adminsystem.entity.Affair;
import com.example.adminsystem.service.AffairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/affair")
public class AffairController {

    @Autowired
    private AffairService affairService;

    @GetMapping("/page")
    public Result page(@RequestParam int current, @RequestParam int size, 
                      @RequestParam(required = false) String title, 
                      @RequestParam(required = false) Integer initiatorId) {
        Page<Affair> page = affairService.page(current, size, title, initiatorId);
        return Result.success(page);
    }

    @GetMapping("/list")
    public Result list() {
        List<Affair> list = affairService.list();
        return Result.success(list);
    }

    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Integer id) {
        Affair affair = affairService.getById(id);
        return Result.success(affair);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Affair affair) {
        boolean success = affairService.save(affair);
        return success ? Result.success() : Result.error("保存失败");
    }

    @PutMapping("/update")
    public Result update(@RequestBody Affair affair) {
        boolean success = affairService.update(affair);
        return success ? Result.success() : Result.error("更新失败");
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = affairService.delete(id);
        return success ? Result.success() : Result.error("删除失败");
    }
}