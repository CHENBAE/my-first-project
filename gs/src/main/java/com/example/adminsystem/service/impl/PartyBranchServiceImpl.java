package com.example.adminsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.entity.PartyBranch;
import com.example.adminsystem.mapper.PartyBranchMapper;
import com.example.adminsystem.service.PartyBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyBranchServiceImpl implements PartyBranchService {

    @Autowired
    private PartyBranchMapper partyBranchMapper;

    @Override
    public Page<PartyBranch> page(int current, int size, String branchName) {
        Page<PartyBranch> page = new Page<>(current, size);
        QueryWrapper<PartyBranch> wrapper = new QueryWrapper<>();
        if (branchName != null && !branchName.isEmpty()) {
            wrapper.like("branch_name", branchName);
        }
        return partyBranchMapper.selectPage(page, wrapper);
    }

    @Override
    public List<PartyBranch> list() {
        return partyBranchMapper.selectList(null);
    }

    @Override
    public PartyBranch getById(Integer id) {
        return partyBranchMapper.selectById(id);
    }

    @Override
    public boolean save(PartyBranch partyBranch) {
        return partyBranchMapper.insert(partyBranch) > 0;
    }

    @Override
    public boolean update(PartyBranch partyBranch) {
        return partyBranchMapper.updateById(partyBranch) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return partyBranchMapper.deleteById(id) > 0;
    }
}