package com.example.adminsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.common.Result;
import com.example.adminsystem.entity.PartyBranch;
import com.example.adminsystem.service.PartyBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/party-branch")
public class PartyBranchController {

    @Autowired
    private PartyBranchService partyBranchService;

    @GetMapping("/page")
    public Result page(@RequestParam int current, @RequestParam int size, 
                      @RequestParam(required = false) String branchName) {
        Page<PartyBranch> page = partyBranchService.page(current, size, branchName);
        return Result.success(page);
    }

    @GetMapping("/list")
    public Result list() {
        List<PartyBranch> list = partyBranchService.list();
        return Result.success(list);
    }

    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Integer id) {
        PartyBranch partyBranch = partyBranchService.getById(id);
        return Result.success(partyBranch);
    }

    @PostMapping("/save")
    public Result save(@RequestBody PartyBranch partyBranch) {
        boolean success = partyBranchService.save(partyBranch);
        return success ? Result.success() : Result.error("保存失败");
    }

    @PutMapping("/update")
    public Result update(@RequestBody PartyBranch partyBranch) {
        boolean success = partyBranchService.update(partyBranch);
        return success ? Result.success() : Result.error("更新失败");
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = partyBranchService.delete(id);
        return success ? Result.success() : Result.error("删除失败");
    }
}