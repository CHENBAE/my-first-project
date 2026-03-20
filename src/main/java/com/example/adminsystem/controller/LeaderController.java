package com.example.adminsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.common.Result;
import com.example.adminsystem.entity.Leader;
import com.example.adminsystem.service.LeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leader")
public class LeaderController {

    @Autowired
    private LeaderService leaderService;

    @GetMapping("/page")
    public Result page(@RequestParam int current, @RequestParam int size, 
                      @RequestParam(required = false) String name, 
                      @RequestParam(required = false) String position) {
        Page<Leader> page = leaderService.page(current, size, name, position);
        return Result.success(page);
    }

    @GetMapping("/list")
    public Result list() {
        List<Leader> list = leaderService.list();
        return Result.success(list);
    }

    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Integer id) {
        Leader leader = leaderService.getById(id);
        return Result.success(leader);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Leader leader) {
        boolean success = leaderService.save(leader);
        return success ? Result.success() : Result.error("保存失败");
    }

    @PutMapping("/update")
    public Result update(@RequestBody Leader leader) {
        boolean success = leaderService.update(leader);
        return success ? Result.success() : Result.error("更新失败");
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = leaderService.delete(id);
        return success ? Result.success() : Result.error("删除失败");
    }
}