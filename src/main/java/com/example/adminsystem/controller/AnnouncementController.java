package com.example.adminsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.common.Result;
import com.example.adminsystem.entity.Announcement;
import com.example.adminsystem.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/page")
    public Result page(@RequestParam int current, @RequestParam int size, 
                      @RequestParam(required = false) String title, 
                      @RequestParam(required = false) Integer publisherId) {
        Page<Announcement> page = announcementService.page(current, size, title, publisherId);
        return Result.success(page);
    }

    @GetMapping("/list")
    public Result list() {
        List<Announcement> list = announcementService.list();
        return Result.success(list);
    }

    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Integer id) {
        Announcement announcement = announcementService.getById(id);
        return Result.success(announcement);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Announcement announcement) {
        boolean success = announcementService.save(announcement);
        return success ? Result.success() : Result.error("保存失败");
    }

    @PutMapping("/update")
    public Result update(@RequestBody Announcement announcement) {
        boolean success = announcementService.update(announcement);
        return success ? Result.success() : Result.error("更新失败");
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = announcementService.delete(id);
        return success ? Result.success() : Result.error("删除失败");
    }
}