package com.example.adminsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.common.Result;
import com.example.adminsystem.entity.Meeting;
import com.example.adminsystem.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meeting")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @GetMapping("/page")
    public Result page(@RequestParam int current, @RequestParam int size, 
                      @RequestParam(required = false) String title, 
                      @RequestParam(required = false) Integer hostId) {
        Page<Meeting> page = meetingService.page(current, size, title, hostId);
        return Result.success(page);
    }

    @GetMapping("/list")
    public Result list() {
        List<Meeting> list = meetingService.list();
        return Result.success(list);
    }

    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Integer id) {
        Meeting meeting = meetingService.getById(id);
        return Result.success(meeting);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Meeting meeting) {
        boolean success = meetingService.save(meeting);
        return success ? Result.success() : Result.error("保存失败");
    }

    @PutMapping("/update")
    public Result update(@RequestBody Meeting meeting) {
        boolean success = meetingService.update(meeting);
        return success ? Result.success() : Result.error("更新失败");
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = meetingService.delete(id);
        return success ? Result.success() : Result.error("删除失败");
    }
}