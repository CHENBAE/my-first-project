package com.example.adminsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.common.Result;
import com.example.adminsystem.entity.TimeNode;
import com.example.adminsystem.service.TimeNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-node")
public class TimeNodeController {

    @Autowired
    private TimeNodeService timeNodeService;

    @GetMapping("/page")
    public Result page(@RequestParam int current, @RequestParam int size, 
                      @RequestParam(required = false) String event, 
                      @RequestParam(required = false) String type) {
        Page<TimeNode> page = timeNodeService.page(current, size, event, type);
        return Result.success(page);
    }

    @GetMapping("/list")
    public Result list() {
        List<TimeNode> list = timeNodeService.list();
        return Result.success(list);
    }

    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Integer id) {
        TimeNode timeNode = timeNodeService.getById(id);
        return Result.success(timeNode);
    }

    @PostMapping("/save")
    public Result save(@RequestBody TimeNode timeNode) {
        boolean success = timeNodeService.save(timeNode);
        return success ? Result.success() : Result.error("保存失败");
    }

    @PutMapping("/update")
    public Result update(@RequestBody TimeNode timeNode) {
        boolean success = timeNodeService.update(timeNode);
        return success ? Result.success() : Result.error("更新失败");
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = timeNodeService.delete(id);
        return success ? Result.success() : Result.error("删除失败");
    }
}