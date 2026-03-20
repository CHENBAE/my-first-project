package com.example.adminsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.common.Result;
import com.example.adminsystem.entity.PartyMember;
import com.example.adminsystem.service.PartyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/party-member")
public class PartyMemberController {

    @Autowired
    private PartyMemberService partyMemberService;

    @GetMapping("/page")
    public Result page(@RequestParam int current, @RequestParam int size, 
                      @RequestParam(required = false) String name, 
                      @RequestParam(required = false) Integer branchId) {
        Page<PartyMember> page = partyMemberService.page(current, size, name, branchId);
        return Result.success(page);
    }

    @GetMapping("/list")
    public Result list() {
        List<PartyMember> list = partyMemberService.list();
        return Result.success(list);
    }

    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Integer id) {
        PartyMember partyMember = partyMemberService.getById(id);
        return Result.success(partyMember);
    }

    @PostMapping("/save")
    public Result save(@RequestBody PartyMember partyMember) {
        boolean success = partyMemberService.save(partyMember);
        return success ? Result.success() : Result.error("保存失败");
    }

    @PutMapping("/update")
    public Result update(@RequestBody PartyMember partyMember) {
        boolean success = partyMemberService.update(partyMember);
        return success ? Result.success() : Result.error("更新失败");
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = partyMemberService.delete(id);
        return success ? Result.success() : Result.error("删除失败");
    }
}